package com.example.app.fragments.settings

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.data.User
import com.example.app.databinding.FragmentSettingsBinding
import com.example.app.databinding.FragmentSignUp1Binding
import com.example.app.fragments.sign_up.FirstSignUpFragment

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
        }


        val menu = binding.bnvMenu
        menu.menu.findItem(R.id.btnm_settings).setIcon(R.drawable.ic_settings2)



        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingsFragment()
    }
}



