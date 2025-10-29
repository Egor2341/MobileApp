package com.example.app.data

import java.time.LocalDate

@kotlinx.serialization.Serializable
data class User (
    val email: String,
    val password: String,
    val last_name: String,
    val first_name: String,
    val patronymic: String,
    val dob: String,
    val gender: String,
    val license_number: String,
    val license_date: String,
    val created_at: String? = null
) : Data