package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentCongratulationsBinding
import com.example.app.databinding.FragmentSignUp3Binding
import com.google.android.material.button.MaterialButton

class CongratulationsFragment : Fragment() {
    private var _binding: FragmentCongratulationsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentCongratulations must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCongratulationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThirdSignUpFragment()
    }
}