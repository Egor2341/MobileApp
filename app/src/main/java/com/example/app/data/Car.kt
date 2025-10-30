package com.example.app.data

@kotlinx.serialization.Serializable
data class Car (
    val type: String,
    val model: String,
    val price: Int,
    val period: String,
    val gearbox: String,
    val fuel: String,
) : Data