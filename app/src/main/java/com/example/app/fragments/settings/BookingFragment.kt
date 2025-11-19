package com.example.app.fragments.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.data.Booking
import com.example.app.databinding.FragmentBookingBinding
import com.example.app.servicies.Bookings
import com.google.gson.Gson
import kotlinx.coroutines.launch

class BookingFragment : Fragment() {

    private var _binding: FragmentBookingBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")

    var activity: MainActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        activity = requireActivity() as? MainActivity

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        parentFragmentManager.setFragmentResultListener(
            "bookings_list",
            this
        ) { key, bundle ->
            updateUI(bundle)
        }

        return binding.root
    }


    private fun updateUI(data: Bundle) {
        val res = Gson().fromJson(data.getString("booking_json"), Booking::class.java)
        binding.apply {
            tvBookingNumber.text = res.id.toString()
            tvType.text = res.car_type
            tvModel.text = res.car_model
            var price = res.rent_price.toString() + "₽"
            tvPrice.text = price
            price += "/день"
            tvRentPrice.text = price
            price = res.insurance_price.toString() + "₽/день"
            tvInsurancePrice.text = price
            tvAddress.text = res.car_address
            tvRentStart.text = res.start
            tvRentEnd.text = res.end
            tvUser.text = res.user_name
            tvLicense.text = res.license
            tvTotal.text = res.total

            binding.btnCancelBooking.setOnClickListener {
                lifecycleScope.launch {
                    Bookings.deleteBooking(res.id ?: 0)
                    activity?.changeFragment(
                        BookingsFragment.newInstance(),
                        R.id.cl_booking
                    )
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookingFragment()
    }
}