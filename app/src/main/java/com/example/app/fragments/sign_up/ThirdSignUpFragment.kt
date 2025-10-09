package com.example.app.fragments.sign_up

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentSignUp3Binding
import com.google.android.material.button.MaterialButton
import android.net.Uri
import android.widget.ImageView
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.fragments.CongratulationsFragment


class ThirdSignUpFragment : Fragment() {

    private var _binding: FragmentSignUp3Binding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentSignUpBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUp3Binding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity
        val btnNext: MaterialButton = binding.btnNext
        btnNext.setOnClickListener {
            activity?.changeFragment(CongratulationsFragment.newInstance(),
                R.id.cl_sign_up3)
        }
        val btnBack: MaterialButton = binding.btnBack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
        val btnAddUserPhoto: ImageButton = binding.btnAddUserPhoto
        val getAvatarContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                val image: ImageView = binding.ivUserPhoto
                image.setImageURI(uri)
            }
        }

        btnAddUserPhoto.setOnClickListener {
            getAvatarContent.launch("image/*")
        }
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {

            }
        }
        val btnAddLicensePhoto: ImageButton = binding.btnAddLicensePhoto
        btnAddLicensePhoto.setOnClickListener {
            getContent.launch("image/*")
        }
        val btnAddPasportPhoto: ImageButton = binding.btnAddPasportPhoto
        btnAddPasportPhoto.setOnClickListener {
            getContent.launch("image/*")
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThirdSignUpFragment()
    }
}