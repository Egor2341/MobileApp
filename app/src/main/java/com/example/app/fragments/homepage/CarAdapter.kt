package com.example.app.fragments.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.Car
import com.example.app.databinding.ItemBinding
import com.google.android.material.button.MaterialButton

class CarAdapter(
    private val cars: List<Car>,
    private val onBookClick: () -> Unit,
    private val onDetailClick: (Car) -> Unit
) :
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val type: TextView = binding.twType
        val model: TextView = binding.twModel
        val price: TextView = binding.twPrice
        val gearbox: TextView = binding.twGearbox
        val fuel: TextView = binding.twFuel
        val btnDetails: MaterialButton = binding.btnDetails
    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val car = cars[position]
        viewHolder.btnDetails.setOnClickListener {
            onDetailClick(car)
        }
        val price = car.price.toString() + "₽"
        viewHolder.type.text = car.type
        viewHolder.model.text = car.model
        viewHolder.price.text = price
        viewHolder.gearbox.text = car.gearbox
        viewHolder.fuel.text = car.fuel
    }

    override fun getItemCount() = cars.size
}