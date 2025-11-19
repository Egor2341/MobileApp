package com.example.app.fragments.homepage

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
import com.example.app.databinding.FragmentHomepageBinding
import com.example.app.fragments.favourites.FavouritesFragment
import com.example.app.fragments.settings.SettingsFragment
import com.example.app.servicies.Cars
import kotlinx.coroutines.launch

class HomepageFragment : Fragment() {

    private var _binding: FragmentHomepageBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomepageBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        val recyclerView: RecyclerView = binding.rwList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        lifecycleScope.launch {
            recyclerView.adapter = CarAdapter(
                Cars.getCars(),
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
                    activity?.changeFragment(ChekoutFragment.newInstance(), R.id.cl_homepage, "home")
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
                        R.id.cl_homepage,
                        "home"
                    )
                })
        }

        val menu = binding.bnvMenu
        menu.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.btnm_main -> {
                    menu.menu.findItem(R.id.btnm_main).setIcon(R.drawable.ic_main2)
                    menu.menu.findItem(R.id.btnm_settings).setIcon(R.drawable.ic_settings1)
                    menu.menu.findItem(R.id.btnm_favorites).setIcon(R.drawable.ic_favorites1)
                    activity?.changeFragment(
                        newInstance(),
                        R.id.cl_settings
                    )
                    true
                }

                R.id.btnm_favorites -> {
                    menu.menu.findItem(R.id.btnm_main).setIcon(R.drawable.ic_main1)
                    menu.menu.findItem(R.id.btnm_settings).setIcon(R.drawable.ic_settings1)
                    menu.menu.findItem(R.id.btnm_favorites).setIcon(R.drawable.ic_favorities2)
                    activity?.changeFragment(
                        FavouritesFragment.newInstance(),
                        R.id.cl_homepage
                    )
                    true
                }

                R.id.btnm_settings -> {
                    menu.menu.findItem(R.id.btnm_main).setIcon(R.drawable.ic_main1)
                    menu.menu.findItem(R.id.btnm_favorites).setIcon(R.drawable.ic_favorites1)
                    menu.menu.findItem(R.id.btnm_settings).setIcon(R.drawable.ic_settings2)
                    activity?.changeFragment(
                        SettingsFragment.newInstance(),
                        R.id.cl_homepage
                    )
                    true
                }

                else -> false
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomepageFragment()
    }

}