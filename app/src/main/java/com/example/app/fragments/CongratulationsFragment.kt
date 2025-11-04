package com.example.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentCongratulationsBinding
import com.example.app.fragments.homepage.HomepageFragment
import com.example.app.fragments.sign_up.ThirdSignUpFragment

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
        val activity = requireActivity() as? MainActivity
        binding.btnNext.setOnClickListener {
            activity?.changeFragment(HomepageFragment.newInstance(), R.id.cl_congratulations)
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = CongratulationsFragment()
    }
}