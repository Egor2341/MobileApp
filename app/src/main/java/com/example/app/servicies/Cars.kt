package com.example.app.servicies

import android.util.Log
import com.example.app.data.Base
import com.example.app.data.Car
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

object Cars {
    suspend inline fun getCars(): List<Car> {
        try {
            val cars = Base.getClient()
                .from("cars")
                .select(
                    columns = Columns.list(
                        "id",
                        "type",
                        "model",
                        "price",
                        "gearbox",
                        "address",
                        "description",
                        "fuel"
                    )
                ){
                    filter {
                        exact("booking_id", null)
                    }
                }
                .decodeList<Car>()

            return cars

        } catch (e: Exception) {
            Log.d("CARS", e.message.toString())
            return listOf()
        }
    }


}