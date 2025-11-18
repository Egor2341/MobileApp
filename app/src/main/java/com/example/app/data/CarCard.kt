package com.example.app.data

@kotlinx.serialization.Serializable
data class CarCard (
    val type: String,
    val model: String,
    val price: Int,
    val gearbox: String,
    val fuel: String
) : Data