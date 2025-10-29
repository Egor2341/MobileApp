package com.example.app.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentProfileBinding
import com.example.app.databinding.FragmentSettingsBinding
import com.example.app.fragments.GettingStartedFragment

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Bindin must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        if (activity != null) {

            val user = activity.getUser();

            val name = user.last_name + " " + user.first_name
            val joined = "Присоединился в " + user.created_at?.substring(0,4)

            Glide.with(requireContext())
                .load(user.avatar.toUri())
                .circleCrop()
                .into(binding.ivUserPhoto)
            binding.twName.setText(name)
            binding.twCreatedAt.setText(joined)
            binding.twEmailValue.setText(user.email)
            binding.twGenderValue.setText(user.gender)
        }


        val menu = binding.bnvMenu
        menu.menu.findItem(R.id.btnm_settings).setIcon(R.drawable.ic_settings2)

        binding.btnSignOut.setOnClickListener {
            activity?.signOut()
            activity?.changeFragment(
                GettingStartedFragment.newInstance(),
                R.id.cl_profile
            )
        }

        return binding.root
    }
    companion object {
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }

}