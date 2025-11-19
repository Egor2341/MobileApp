package com.example.app.data

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Booking (
    val start: String,
    val end: String,
    val user_id: Int?,
    val car_id: Int,
    val car_type: String,
    val car_model: String,
    val rent_price: Int,
    val car_address: String,
    val user_name: String,
    val license: String,
    val duration: String,
    val insurance_price: Int,
    val total: String,
    val id: Int? = 0
) : Data