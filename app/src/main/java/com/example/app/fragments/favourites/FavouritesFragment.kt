package com.example.app.fragments.favourites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.databinding.FragmentFavouritesBinding
import com.example.app.fragments.homepage.CarAdapter
import com.example.app.fragments.homepage.ChekoutFragment
import com.example.app.fragments.homepage.DetailsFragment
import com.example.app.servicies.Cars
import com.example.app.servicies.Fav
import kotlinx.coroutines.launch

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentCongratulations must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity
        val recyclerView: RecyclerView = binding.rwList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            recyclerView.adapter = CarAdapter(
                Cars.getFavCars(Fav.getIdCars(activity?.getUserId() ?: 0)),
                onBookClick = { car ->
                    parentFragmentManager.setFragmentResult(
                        "booking",
                        bundleOf(
                            "id" to car.id,
                            "type" to car.type,
                            "model" to car.model,
                            "address" to car.address,
                            "description" to car.description,
                            "price" to car.price
                        )
                    )
                    activity?.changeFragment(
                        ChekoutFragment.newInstance(),
                        R.id.cl_favourites,
                        "fav"
                    )
                },
                onDetailClick = { car ->
                    parentFragmentManager.setFragmentResult(
                        "details",
                        bundleOf(
                            "id" to car.id,
                            "type" to car.type,
                            "model" to car.model,
                            "address" to car.address,
                            "description" to car.description,
                            "price" to car.price,
                            "insurance" to car.insurance
                        )
                    )
                    activity?.changeFragment(
                        DetailsFragment.newInstance(),
                        R.id.cl_favourites,
                        "fav"
                    )
                })
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
}