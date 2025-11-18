package com.example.app.data

import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Booking (
    val start: String,
    val end: String,
    val user_id: Int?,
    val car_id: Int
) : Data