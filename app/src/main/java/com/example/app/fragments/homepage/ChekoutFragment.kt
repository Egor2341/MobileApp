package com.example.app.fragments.homepage

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.MainActivity
import com.example.app.R
import com.example.app.data.Booking
import com.example.app.databinding.FragmentCheckoutBinding
import com.example.app.servicies.Insert
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ChekoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding must not be null")

    private var startDateTime = java.time.LocalDateTime.now()
    private var endDateTime = java.time.LocalDateTime.now()
    private var rentPrice: Int = 0
    private var insurancePrice: Int = 0

    private var startDate: String = ""
    private var endDate: String = ""
    private var carId: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        val activity = requireActivity() as? MainActivity

        parentFragmentManager.setFragmentResultListener(
            "booking",
            this
        ) { key, bundle ->
            updateUI(bundle)
        }

        val rentStart = binding.btnRentStart
        rentStart.setOnClickListener {
            showDateTimePicker { selectedCalendar ->

                if (selectedCalendar.time.time > System.currentTimeMillis()) {
                    binding.tvError.text = ""
                    startDateTime = selectedCalendar.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                    startDate = formatDateTimeWithCalendar(selectedCalendar.time)
                    rentStart.text = startDate
                } else {
                    binding.tvError.text = "Некорректная дата"
                }
            }
        }

        val rentEnd = binding.btnRentEnd
        rentEnd.setOnClickListener {
            showDateTimePicker { selectedCalendar ->

                if (selectedCalendar.time.time > System.currentTimeMillis()) {
                    binding.tvError.text = ""
                    endDateTime = selectedCalendar.toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                    endDate = formatDateTimeWithCalendar(selectedCalendar.time)
                    rentEnd.text = endDate
                    val daysDifference = ChronoUnit.DAYS
                        .between(
                            startDateTime,
                            endDateTime
                        ).toInt()
                    if (daysDifference >= 1) {
                        var text = daysDifference.toString() + "x "
                        if (daysDifference == 1) {
                            text += "день"
                        } else if (daysDifference < 5) {
                            text += "дня"
                        } else {
                            text += "дней"
                        }
                        binding.tvRentCountDays.text = text
                        binding.tvInsuranceCountDays.text = text
                        val total  = (rentPrice * daysDifference).toString() + "₽"
                        binding.tvTotal.text = total
                    }
                } else {
                    binding.tvError.text = "Некорректная дата"
                }
            }
        }

        binding.btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnNext.setOnClickListener {
            if (binding.tvError.text.equals("")){
                activity?.changeFragment(SuccessfulFragment.newInstance(),
                    R.id.cl_chekout)
                lifecycleScope.launch {
                    Insert.insertData("bookings",
                        Booking(startDate, endDate,
                            activity?.getUser()?.id, carId))
                }
            }
        }

        return binding.root
    }

    private fun updateUI(data: Bundle) {
        binding.apply {
            carId = data.getInt("id")
            tvType.text = data.getString("type")
            tvModel.text = data.getString("model")
            tvAddress.text = data.getString("address")
            rentPrice = data.getInt("price")
            var price = rentPrice.toString() + "₽"
            tvPrice.text = price
            price = rentPrice.toString() + "₽/день"
            tvRentPrice.text =price
            insurancePrice = data.getInt("insurance")
            val insurance = insurancePrice.toString() + "₽/день"
            tvInsurancePrice.text = insurance
        }

    }


    fun formatDateTimeWithCalendar(date: Date): String {
        val formatter = SimpleDateFormat("HH:mm, d MMMM yyyy", Locale.forLanguageTag("ru"))
        return formatter.format(date)
    }

    private fun showDateTimePicker(onDateTimeSelected: (Calendar) -> Unit) {
        val currentCalendar = Calendar.getInstance()

        DatePickerDialog(
            requireContext(),
            { _, year, month, day ->
                val dateCalendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }

                TimePickerDialog(
                    requireContext(),
                    { _, hour, minute ->
                        dateCalendar.set(Calendar.HOUR_OF_DAY, hour)
                        dateCalendar.set(Calendar.MINUTE, minute)

                        onDateTimeSelected(dateCalendar)
                    },
                    currentCalendar.get(Calendar.HOUR_OF_DAY),
                    currentCalendar.get(Calendar.MINUTE),
                    true
                ).show()
            },
            currentCalendar.get(Calendar.YEAR),
            currentCalendar.get(Calendar.MONTH),
            currentCalendar.get(Calendar.DAY_OF_MONTH)
        ).show()
    }


    companion object {
        @JvmStatic
        fun newInstance() = ChekoutFragment()
    }
}