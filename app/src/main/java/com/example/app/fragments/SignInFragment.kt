package com.example.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentSignInBinding
import com.example.app.fragments.sign_up.FirstSignUpFragment
import com.google.android.material.button.MaterialButton

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentSignInBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val buttonSignUp: MaterialButton = binding.btnSignUp
        val activity = requireActivity() as? MainActivity
        buttonSignUp.setOnClickListener {
            activity?.changeFragment(FirstSignUpFragment(), R.id.cl_sign_in)
        }
        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}