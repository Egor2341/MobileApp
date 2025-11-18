package com.example.app.data

@kotlinx.serialization.Serializable
data class Car (
    val id: Int,
    val type: String,
    val model: String,
    val price: Int,
    val gearbox: String,
    val address: String,
    val description: String,
    val fuel: String
) : Data