package com.example.app.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentSettingsBinding
import com.example.app.fragments.homepage.HomepageFragment

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Bindin must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        if (activity != null) {

            val name = activity.getUser().last_name + " " + activity.getUser().first_name

            binding.twSettingsName.setText(name)
            binding.twSettingsEmail.setText(activity.getUser().email)

            Glide.with(requireContext())
                .load(activity.getUser().avatar.toUri())
                .circleCrop()
                .into(binding.ivUserPhoto)

        }

        binding.btnProfile.setOnClickListener {
            activity?.changeFragment(
                ProfileFragment.newInstance(),
                R.id.cl_settings
            )
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}



