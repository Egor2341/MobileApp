package com.example.app.fragments.sign_up

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentSignUp2Binding
import com.example.app.fragments.GettingStartedFragment
import com.google.android.material.button.MaterialButton
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SecondSignUpFragment : Fragment() {

    private var _binding: FragmentSignUp2Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentSignUpBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSignUp2Binding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
//            if (checkFields()) {
                var gender: String
                if (binding.btnMan.isChecked){
                    gender = binding.btnMan.text.toString()
                } else {
                    gender = binding.btnWoman.text.toString()
                }
                parentFragmentManager.setFragmentResult(
                    "secondPage",
                    bundleOf(
                                    "lastName" to binding.etLastname.text.toString(),
                        "firstName" to binding.etFirstname.text.toString(),
                        "patronymic" to binding.etPatronymic.text.toString(),
                        "dob" to binding.etDate.text.toString(),
                        "gender" to gender
                                )
                )

                activity?.changeFragment(
                    ThirdSignUpFragment.newInstance(),
                    R.id.cl_sign_up2, "second_signup"
                )
//            }
        }

        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
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
        return binding.root
    }

    private fun checkFields(): Boolean {
        return when {
            binding.etLastname.text.toString().isEmpty() -> {
                binding.etLastname.error = "Обязательное поле"
                false
            }

            binding.etFirstname.text.toString().isEmpty() -> {
                binding.etFirstname.error = "Обязательное поле"
                false
            }

            binding.etPatronymic.text.toString().isEmpty() -> {
                binding.etPatronymic.error = "Обязательное поле"
                false
            }

            binding.etDate.text.toString().isEmpty() -> {
                binding.etDate.error = "Обязательное поле"
                false
            }

            !isValidDate(binding.etDate.text.toString()) -> {
                binding.etDate.error = "Некорректная дата"
                false
            }

            else -> {
                binding.etFirstname.error = null
                binding.etLastname.error = null
                binding.etPatronymic.error = null
                binding.etDate.error
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


    companion object {
        @JvmStatic
        fun newInstance() = SecondSignUpFragment()
    }
}