package com.example.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentGettingStartedBinding
import com.example.app.fragments.sign_up.FirstSignUpFragment
import com.google.android.material.button.MaterialButton

class GettingStartedFragment : Fragment() {

    private var _binding: FragmentGettingStartedBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentGettingStartedBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGettingStartedBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity
        val btnSignIn: MaterialButton = binding.btnSignIn

        btnSignIn.setOnClickListener {
            activity?.changeFragment(SignInFragment.newInstance(),
                R.id.cl_getting_started)
        }
        val buttonSignUp: MaterialButton = binding.btnSignUp
        buttonSignUp.setOnClickListener {
            activity?.changeFragment(FirstSignUpFragment.newInstance(),
                R.id.cl_getting_started, "getting_start")
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = GettingStartedFragment()
    }
}