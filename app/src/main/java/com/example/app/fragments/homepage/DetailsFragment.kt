package com.example.app.fragments.homepage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.MainActivity
import com.example.app.databinding.FragmentDetailsBinding
import com.example.app.servicies.Cars
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity


        parentFragmentManager.setFragmentResultListener(
            "details",
            this
        ) { key, bundle ->
            updateUI(bundle)
        }

        return binding.root
    }

    private fun updateUI(data: Bundle){
        binding.apply {
            val name = data.getString("model") + data.getString("type")
            tvName.text = name
            tvAddress.text = data.getString("address")
            tvDescription.text = data.getString("description")
            val price = data.getInt("price").toString() + "₽/день"
            tvPrice.text = price
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}