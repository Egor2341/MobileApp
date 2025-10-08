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
            if (checkFields()) {
                activity?.changeFragment(SecondSignUpFragment.newInstance(), R.id.cl_sign_up2, "first_signup")
            }
        }
        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        return binding.root
    }

    private fun checkFields(): Boolean {
        val emailPattern = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        val mail: TextInputEditText = binding.etMail
        if (!emailPattern.matches(mail.text.toString())) {
            return false
        }
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstSignUpFragment()
    }
}