package com.example.app.fragments.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentSignUp1Binding
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlin.text.Regex

class FirstSignUpFragment : Fragment() {

    private var _binding: FragmentSignUp1Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentSignUpBinding must not be null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUp1Binding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity
        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
//            if (checkFields()) {
                activity?.changeFragment(SecondSignUpFragment.newInstance(),
                    R.id.cl_sign_up1, "first_signup")
//            }
        }
        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun checkFields(): Boolean {
        return when {
            !validateEmail() -> {
                false
            }
            !validatePassword() -> {
                false
            }
            !binding.etPassword.text.toString()
                .equals(binding.etRepeatPassword.text.toString()) -> {
                    binding.etRepeatPassword.error = "Пароли не совпадают"
                false
            }
            !binding.chbConfidence.isChecked -> {
                binding.chbConfidence.error = ""
                false
            }
            else -> {
                binding.etRepeatPassword.error = null
                binding.chbConfidence.error = null
                true
            }
        }
    }

    fun validateEmail() : Boolean {
        val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val email: TextInputEditText = binding.etMail
        if (!emailPattern.matches(email.text.toString())) {
            email.error = "Введите корректный email"
            return false
        }
        email.error = null
        return true
    }

    fun validatePassword(): Boolean {
        val password = binding.etPassword
        val passwordText = password.text.toString()

        val minLength = 8
        val hasDigit = passwordText.any { it.isDigit() }
        val hasUpperCase = passwordText.any { it.isUpperCase() }
        val hasSpecialChar = passwordText.any { "!@#$%^&*()-_=+<>?/{}~|".contains(it) }

        return when {
            passwordText.isEmpty() -> {
                password.error = "Введите пароль"
                false
            }
            passwordText.length < minLength -> {
                password.error = "Пароль должен быть не менее $minLength символов"
                false
            }
            !hasDigit -> {
                password.error = "Пароль должен содержать хотя бы одну цифру"
                false
            }
            !hasUpperCase -> {
                password.error = "Пароль должен содержать хотя бы одну заглавную букву"
                false
            }
            !hasSpecialChar -> {
                password.error = "Пароль должен содержать хотя бы один специальный символ"
                false
            }
            else -> {
                password.error = null
                true
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstSignUpFragment()
    }
}