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
        val btnSignIn: MaterialButton = binding.btnSignIn

        btnSignIn.setOnClickListener {
            changeFragment(SignInFragment())
        }
        val buttonSignUp: MaterialButton = binding.btnSignUp
        buttonSignUp.setOnClickListener {
            changeFragment(FirstSignUpFragment())
        }
        return binding.root
    }

    private fun changeFragment(fragment: Fragment){
        val activity = requireActivity() as? MainActivity
        if (activity != null) {
            if (activity.isInternet()) {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.replace(R.id.cl_getting_started, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            } else {
                activity.noConFragment(R.id.cl_getting_started)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GettingStartedFragment()
    }
}