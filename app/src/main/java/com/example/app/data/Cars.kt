package com.example.app.data

object Cars {
    val cars = mutableListOf<Car>()
    init {
        val car1 = Car(
            "S 500 Sedan", "Mercedes Benz",
            1000, "в день", "A/T", "бензин"
        )
        val car2 = Car(
            "S 500 Sedan", "Mercedes Benz",
            1000, "в день", "A/T", "бензин"
        )
        val car3 = Car(
            "S 500 Sedan", "Mercedes Benz",
            1000, "в день", "A/T", "бензин"
        )
        val car4 = Car(
            "S 500 Sedan", "Mercedes Benz",
            1000, "в день", "A/T", "бензин"
        )
        val car5 = Car(
            "S 500 Sedan", "Mercedes Benz",
            1000, "в день", "A/T", "бензин"
        )
        val car6 = Car(
            "S 500 Sedan", "Mercedes Benz",
            1000, "в день", "A/T", "бензин"
        )
        cars.addAll(listOf(car1, car2, car3, car4, car5, car6))
    }

}