package com.example.app.servicies

import android.util.Log
import com.example.app.data.Base
import com.example.app.data.CarCard
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

object AllCars {
    suspend inline fun getCars(): List<CarCard> {
        try {
            val cars = Base.getClient()
                .from("cars")
                .select(
                    columns = Columns.list(
                        "type",
                        "model",
                        "price",
                        "gearbox",
                        "fuel"
                    )
                ).decodeList<CarCard>()

            return cars

        } catch (e: Exception) {
            Log.d("CARS", e.message.toString())
            return listOf()
        }
    }
}