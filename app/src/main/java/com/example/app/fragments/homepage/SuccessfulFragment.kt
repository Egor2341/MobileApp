package com.example.app.fragments.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentDetailsBinding
import com.example.app.databinding.FragmentSuccessfulBinding
import com.example.app.fragments.settings.BookingsFragment

class SuccessfulFragment : Fragment() {

    private var _binding: FragmentSuccessfulBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")

    private var activity: MainActivity? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessfulBinding.inflate(inflater, container, false)
        activity = requireActivity() as? MainActivity

        binding.btnHome.setOnClickListener {
            activity?.changeFragment(HomepageFragment.newInstance(), R.id.cl_successful)
        }

        binding.btnBookings.setOnClickListener {
            activity?.changeFragment(BookingsFragment.newInstance(), R.id.cl_successful)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SuccessfulFragment()
    }
}