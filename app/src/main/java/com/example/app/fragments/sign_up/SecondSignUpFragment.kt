package com.example.app.fragments.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.FragmentSignUp2Binding
import com.google.android.material.button.MaterialButton

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
        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
            val newFragment = ThirdSignUpFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.cl_sign_up2, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            val newFragment = FirstSignUpFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.cl_sign_up2, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondSignUpFragment()
    }
}