package com.example.app.fragments.sign_up

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentSignUp3Binding
import com.google.android.material.button.MaterialButton
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.data.User
import com.example.app.fragments.CongratulationsFragment
import com.example.app.servicies.Insert
import com.example.app.servicies.UserImages
import kotlinx.coroutines.launch
import java.io.File
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Calendar
import java.util.Locale


class ThirdSignUpFragment : Fragment() {

    private var _binding: FragmentSignUp3Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentSignUpBinding must not be null")

    var curPhoto = ""
    var licensePhoto = false
    var passportPhoto = false

    var photos = arrayOfNulls<Uri>(3)

    lateinit var imageUri: Uri

    val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                savePhoto(uri)
            }
        }

    val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                savePhoto(imageUri)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUp3Binding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        var firstPage = Bundle()
        var secondPage = Bundle()

        parentFragmentManager.setFragmentResultListener(
            "firstPage",
            this
        ) { key, bundle ->
            firstPage = bundle
        }

        parentFragmentManager.setFragmentResultListener(
            "secondPage",
            this
        ) { key, bundle ->
            secondPage = bundle
        }

        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
            if (checkFields()) {

            val email = firstPage.getString("email")
                ?: throw java.lang.IllegalStateException("email can't be null")
            val user = User(
                email,
                firstPage.getString("password")
                    ?: throw java.lang.IllegalStateException("email can't be null"),
                secondPage.getString("lastName")
                    ?: throw java.lang.IllegalStateException("lastName can't be null"),
                secondPage.getString("firstName")
                    ?: throw java.lang.IllegalStateException("firstName can't be null"),
                secondPage.getString("patronymic")
                    ?: throw java.lang.IllegalStateException("patronymic can't be null"),
                convertToDifferentFormatDate(secondPage.getString("dob"))
                    ?: throw java.lang.IllegalStateException("dob can't be null"),
                secondPage.getString("gender")
                    ?: throw java.lang.IllegalStateException("gender can't be null"),
                binding.etLicenseNumber.text.toString(),
                convertToDifferentFormatDate(binding.etDate.text.toString())
                    ?: throw java.lang.IllegalStateException("licenseDate can't be null"),
                photos[0].toString()
            )

            lifecycleScope.launch {
                Insert.insertData("users", user)
            }

//                uploadImages("test")

                activity?.setUser(user)

            activity?.changeFragment(
                CongratulationsFragment.newInstance(),
                R.id.cl_sign_up3
            )

            }
        }

        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val btnAddUserPhoto: ImageButton = binding.btnAddUserPhoto

        btnAddUserPhoto.setOnClickListener {
            curPhoto = "user"
            showImageSourceDialog()
        }

        val date = binding.etDate
        date.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, selectedYear, selectedMonth, selectedDay ->
                    date.setText(
                        getString(
                            R.string.date_format,
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )
                    )

                },
                year, month, day
            )
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        val btnAddLicensePhoto: ImageButton = binding.btnAddLicensePhoto
        btnAddLicensePhoto.setOnClickListener {
            showImageSourceDialog()
            curPhoto = "license"
        }

        val btnAddPasportPhoto: ImageButton = binding.btnAddPasportPhoto
        btnAddPasportPhoto.setOnClickListener {
            showImageSourceDialog()
            curPhoto = "passport"
        }

        return binding.root
    }


    fun uriToByteArray(uri: Uri): ByteArray {
        val inputStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
        val imageBytes = inputStream?.readBytes()
        inputStream?.close()
        if (imageBytes == null) {
            throw java.lang.IllegalStateException("Image can't be null")
        }
        return imageBytes
    }

    fun uploadImages(email: String) {
        lifecycleScope.launch {
//            for (photo in photos) {
//                UserImages.newInstance().uploadImage(
//                    email,
//                    uriToByteArray(
//                        photo
//                            ?: throw java.lang.IllegalStateException("Image can't be null")
//                    )
//                )
//            }
            UserImages.uploadImage(
                email,
                uriToByteArray(
                    photos[0]
                        ?: throw java.lang.IllegalStateException("Image can't be null")
                )
            )

        }
    }

    fun convertToDifferentFormatDate(dateString: String?): String? {
        return try {
            if (dateString.isNullOrEmpty()) return null

            val formats = listOf(
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("dd/M/yyyy"),
                DateTimeFormatter.ofPattern("d/MM/yyyy")
            )

            var parsedDate: LocalDate? = null
            for (format in formats) {
                try {
                    parsedDate = LocalDate.parse(dateString, format)
                    break
                } catch (e: DateTimeParseException) {

                }
            }

            parsedDate?.format(DateTimeFormatter.ISO_LOCAL_DATE)
        } catch (e: Exception) {
            null
        }
    }

    private fun checkFields(): Boolean {
        return when {
            !licensePhoto || !passportPhoto -> {
                binding.twError.setText("Пожалуйста, загрузите все необходимые фото")
                false
            }

            binding.etLicenseNumber.text.toString().isEmpty() ||
                    binding.etDate.text.toString().isEmpty() -> {
                binding.twError.setText("Пожалуйста, заполните все обязательные поля")
                false
            }

            binding.etLicenseNumber.text.toString().length != 10 -> {
                binding.etLicenseNumber.error = "Введите корректный номер"
                false
            }

            !isValidDate(binding.etDate.text.toString()) -> {
                binding.etDate.error = "Введите корректную дату выдачи."
                false
            }

            else -> {
                binding.twError.setText("")
                binding.etLicenseNumber.error = null
                binding.etDate.error = null
                true
            }
        }
    }

    private fun isValidDate(date: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false
        return try {
            val parsedDate = dateFormat.parse(date)
            parsedDate != null
        } catch (e: Exception) {
            false
        }
    }

    fun showImageSourceDialog() {
        val options = arrayOf("Галерея", "Камера")
        AlertDialog.Builder(requireContext())
            .setTitle("Выберите источник фотографии")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            .show()
    }

    fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    fun openCamera() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                1000
            )
            return
        }
        val file = File(
            requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
            "photo_${System.currentTimeMillis()}.jpg"
        )
        imageUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
        cameraLauncher.launch(imageUri)
    }

    private fun savePhoto(uri: Uri) {
        if (curPhoto.equals("license")) {
            photos[1] = uri
            licensePhoto = true
        } else if (curPhoto.equals("passport")) {
            photos[2] = uri
            passportPhoto = true
        } else {
            val image: ImageView = binding.ivUserPhoto
            Glide.with(requireContext())
                .load(uri)
                .circleCrop()
                .into(image)
            photos[0] = uri
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThirdSignUpFragment()
    }
}