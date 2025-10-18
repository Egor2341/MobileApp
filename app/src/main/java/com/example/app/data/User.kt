package com.example.app.data

@kotlinx.serialization.Serializable
data class User (
    val email: String = "",
    val password: String = "",
    val lastName: String = "",
    val firstName: String = "",
    val patronymic: String = "",
    val dob: String = "",
    val gender: String = "",
    val licenseNumber: String = "",
    val licenseDate: String = ""
) : Data