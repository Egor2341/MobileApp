package com.example.app.data

@kotlinx.serialization.Serializable
data class Favourite (
    val user_id: Int,
    val car_id: Int
)