package com.example.app.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentOnboarding2Binding
import com.example.app.fragments.GettingStartedFragment
import com.google.android.material.button.MaterialButton


class SecondOnboardingFragment : Fragment() {

    private var _binding: FragmentOnboarding2Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentOnboardingBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding2Binding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity
        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
            activity?.changeFragment(ThirdOnboardingFragment(), R.id.cl_onboarding2)
        }
        val btnBack: MaterialButton = binding.btnSkip
        btnBack.setOnClickListener {
            activity?.changeFragment(GettingStartedFragment(), R.id.cl_onboarding2)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SecondOnboardingFragment()
    }

}

