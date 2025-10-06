package com.example.app.fragments.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentSignUp1Binding
import com.example.app.fragments.GettingStartedFragment
import com.google.android.material.button.MaterialButton

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
            activity?.changeFragment(SecondSignUpFragment(), R.id.cl_sign_up1)
        }
        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            activity?.changeFragment(GettingStartedFragment(), R.id.cl_sign_up1)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FirstSignUpFragment()
    }
}