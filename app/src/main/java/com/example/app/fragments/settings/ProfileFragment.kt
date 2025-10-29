package com.example.app.fragments.settings

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentProfileBinding
import com.example.app.databinding.FragmentSettingsBinding
import com.example.app.fragments.GettingStartedFragment
import com.example.app.servicies.UpdateAvatar
import kotlinx.coroutines.launch
import java.io.File

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Bindin must not be null")

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
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        if (activity != null) {

            val user = activity.getUser();

            val name = user.last_name + " " + user.first_name
            val joined = "Присоединился в " + user.created_at?.substring(0, 4)

            Glide.with(requireContext())
                .load(user.avatar.toUri())
                .circleCrop()
                .into(binding.ivUserPhoto)
            binding.twName.setText(name)
            binding.twCreatedAt.setText(joined)
            binding.twEmailValue.setText(user.email)
            binding.twGenderValue.setText(user.gender)
        }

        val btnAddUserPhoto: ImageButton = binding.btnAddUserPhoto

        btnAddUserPhoto.setOnClickListener {
            showImageSourceDialog()
        }


        val menu = binding.bnvMenu
        menu.menu.findItem(R.id.btnm_settings).setIcon(R.drawable.ic_settings2)

        binding.btnSignOut.setOnClickListener {
            activity?.signOut()
            activity?.changeFragment(
                GettingStartedFragment.newInstance(),
                R.id.cl_profile
            )
        }

        return binding.root
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

        val image: ImageView = binding.ivUserPhoto
        Glide.with(requireContext())
            .load(uri)
            .circleCrop()
            .into(image)

        lifecycleScope.launch {
            val res = UpdateAvatar.newInstance()
                .update(uri.toString(), binding.twEmailValue.text.toString())
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

}