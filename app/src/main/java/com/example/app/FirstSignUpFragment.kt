package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentGettingStartedBinding
import com.example.app.databinding.FragmentSignUp1Binding
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
        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
            val newFragment = SecondSignUpFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.cl_sign_up1, newFragment)
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
        fun newInstance() = FirstSignUpFragment()
    }
}