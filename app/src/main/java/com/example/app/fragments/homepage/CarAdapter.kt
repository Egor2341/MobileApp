package com.example.app.fragments.homepage

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.CarCard
import com.example.app.databinding.ItemBinding

class CarAdapter(private val cars: List<CarCard>):
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val type: TextView = binding.twType
        val model: TextView = binding.twModel
        val price: TextView = binding.twPrice
        val gearbox: TextView = binding.twGearbox
        val fuel: TextView = binding.twFuel
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val price = cars[position].price.toString() + "₽"
        viewHolder.type.text = cars[position].type
        viewHolder.model.text = cars[position].model
        viewHolder.price.text = price
        viewHolder.gearbox.text = cars[position].gearbox
        viewHolder.fuel.text = cars[position].fuel
    }

    override fun getItemCount() = cars.size
}