package com.example.app.fragments.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.FragmentOnboarding1Binding
import com.example.app.fragments.GettingStartedFragment
import com.google.android.material.button.MaterialButton


class FirstOnboardingFragment : Fragment() {

    private var _binding: FragmentOnboarding1Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentOnboardingBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboarding1Binding.inflate(inflater, container, false)
        val buttonNext: MaterialButton = binding.btnNext
        buttonNext.setOnClickListener {
            val newFragment = SecondOnboardingFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.cl_onboarding1, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        val buttonSkip: MaterialButton = binding.btnSkip
        buttonSkip.setOnClickListener {
            val newFragment = GettingStartedFragment()
            val transaction = parentFragmentManager.beginTransaction()
            transaction.replace(R.id.cl_onboarding1, newFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        return binding.root
    }




    companion object {
        @JvmStatic
        fun newInstance() = FirstOnboardingFragment()
    }

}

