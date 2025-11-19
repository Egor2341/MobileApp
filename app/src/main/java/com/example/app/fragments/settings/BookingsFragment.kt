package com.example.app.fragments.settings

import android.os.Bundle
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
import com.example.app.databinding.FragmentBookingsBinding
import com.example.app.fragments.homepage.CarAdapter
import com.example.app.fragments.homepage.ChekoutFragment
import com.example.app.servicies.Bookings
import com.example.app.servicies.Cars
import com.google.gson.Gson
import kotlinx.coroutines.launch

class BookingsFragment : Fragment() {

    private var _binding: FragmentBookingsBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Bindin must not be null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingsBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val recyclerView: RecyclerView = binding.rwList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val gson = Gson()
        lifecycleScope.launch {
            recyclerView.adapter = BookingsAdapter(
                Bookings.getBookings(activity?.getUserId() ?: 0),
                onClick = { booking ->
                    parentFragmentManager.setFragmentResult(
                        "bookings_list",
                        bundleOf(
                            "booking_json" to gson.toJson(booking)
                        )
                    )
                    activity?.changeFragment(
                        BookingFragment.newInstance(),
                        R.id.cl_bookings,
                        "bookings_list"
                    )
                })
        }


        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookingsFragment()
    }
}