package com.example.app.fragments.settings

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.Booking
import com.example.app.data.Car
import com.example.app.databinding.BookingBinding
import com.example.app.databinding.ItemBinding
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class BookingsAdapter(
    private val bookings: List<Booking>,
    private val onClick: (Booking) -> Unit
) :
    RecyclerView.Adapter<BookingsAdapter.ViewHolder>() {

    class ViewHolder(binding: BookingBinding) : RecyclerView.ViewHolder(binding.root) {
        val card: MaterialCardView = binding.cvCard
        val title: TextView = binding.tvTitle
        val status: TextView = binding.tvRentStatus
        val date: TextView = binding.tvRentDate
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = BookingBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d("BOOK", bookings.toString())
        val booking = bookings[position]
        val title = booking.car_model + " " + booking.car_type
        viewHolder.title.text = title
        var date = booking.start
        var status = "Начало аренды: "
        if (parseRussianDate(booking.end).isBefore(LocalDateTime.now())) {
            status = "Аренда завершена"
            date = date.substring(0, 5)
        }
        viewHolder.status.text = status
        viewHolder.date.text = date
        viewHolder.card.setOnClickListener {
            onClick(booking)
        }
    }

    override fun getItemCount() = bookings.size

    private  fun parseRussianDate(dateString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm, d MMMM yyyy",
            Locale.forLanguageTag("ru"))
        return LocalDateTime.parse(dateString, formatter)
    }
}