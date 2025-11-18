package com.example.app.fragments.homepage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentDetailsBinding
import com.example.app.servicies.Fav
import com.example.app.servicies.Insert
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")

    private var activity: MainActivity? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        activity = requireActivity() as? MainActivity

        parentFragmentManager.setFragmentResultListener(
            "details",
            this
        ) { key, bundle ->
            updateUI(bundle)
        }

        val btnBack = binding.btnBack
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        return binding.root
    }

    private fun updateUI(data: Bundle) {
        binding.apply {
            if (data.isEmpty) {
                tvError.text =
                    "Не удалось загрузить данные. Попробуйте снова."

                return
            } else {
                tvError.text = ""
            }
            val name = data.getString("model") + data.getString("type")
            tvName.text = name
            tvAddress.text = data.getString("address")
            tvDescription.text = data.getString("description")
            val price = data.getInt("price").toString() + "₽/день"
            tvPrice.text = price
            lifecycleScope.launch {
                val res = Fav.getState(
                    activity?.getUser()?.id,
                    data.getInt("id")
                )
                if (res == 0) {
                    btnFav.setImageResource(R.drawable.ic_heart)
                } else if (res == 1) {
                    btnFav.setImageResource(R.drawable.ic_heart1)
                }
            }
            btnFav.setOnClickListener {
                lifecycleScope.launch {
                    val res = Fav.favourite(
                        activity?.getUser()?.id,
                        data.getInt("id")
                    )
                    if (res == 0) {
                        btnFav.setImageResource(R.drawable.ic_heart)
                        tvError.text = ""
                    } else if (res == 1) {
                        btnFav.setImageResource(R.drawable.ic_heart1)
                        tvError.text = ""
                    } else {
                        tvError.text =
                            "«Не удалось добавить в избранное. Попробуйте снова"
                    }
                }
            }
        }
    }


    companion object {
        @JvmStatic
        fun newInstance() = DetailsFragment()
    }
}