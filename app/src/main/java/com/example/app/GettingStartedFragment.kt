package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentGettingStartedBinding

class GettingStartedFragment : Fragment() {

    private var _binding: FragmentGettingStartedBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentGettingStartedBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGettingStartedBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = GettingStartedFragment()
    }
}