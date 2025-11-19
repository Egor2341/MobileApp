package com.example.app.servicies

import android.util.Log
import com.example.app.data.Base
import com.example.app.data.Booking
import io.github.jan.supabase.postgrest.from

object Bookings {
    suspend inline fun getBookings(userId: Int): List<Booking> {
        try {
            val bookings = Base.getClient()
                .from("bookings")
                .select(){
                    filter {
                        eq("user_id", userId)
                    }
                }
                .decodeList<Booking>()

            return bookings

        } catch (e: Exception) {
            Log.d("BOOKING", e.message.toString())
            return listOf()
        }
    }
}