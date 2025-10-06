package com.example.app.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentOnboarding3Binding
import com.example.app.fragments.GettingStartedFragment
import com.google.android.material.button.MaterialButton

class ThirdOnboardingFragment : Fragment() {
    private var _binding: FragmentOnboarding3Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentOnboardingBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding3Binding.inflate(inflater, container, false)
        val buttonFinish: MaterialButton = binding.btnFinish
        buttonFinish.setOnClickListener {
            clickButton()
        }
        val buttonSkip: MaterialButton = binding.btnSkip
        buttonSkip.setOnClickListener {
            clickButton()
        }
        return binding.root
    }

    private fun clickButton() {
        val activity = requireActivity() as? MainActivity
            activity?.changeFragment(GettingStartedFragment(), R.id.cl_onboarding3)
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThirdOnboardingFragment()
    }
}