package com.example.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentGettingStartedBinding
import com.example.app.databinding.FragmentNoConnectionBinding
import com.example.app.fragments.sign_up.FirstSignUpFragment
import com.google.android.material.button.MaterialButton

class NoConnectionFragment : Fragment() {

    private var _binding: FragmentNoConnectionBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentNoConnectionBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNoConnectionBinding.inflate(inflater, container, false)

        val btnRetry: MaterialButton = binding.btnRetry
        btnRetry.setOnClickListener {
            val activity = requireActivity() as? MainActivity
            if (activity != null) {
                if (activity.isInternet()) {
                    parentFragmentManager.popBackStack()
                }
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = NoConnectionFragment()
    }
}