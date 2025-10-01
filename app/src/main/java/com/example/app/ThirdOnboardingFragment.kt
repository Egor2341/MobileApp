package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentOnboarding3Binding
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
        val newFragment = GettingStartedFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.cl_onboarding3, newFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThirdOnboardingFragment()
    }
}