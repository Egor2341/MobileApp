package com.example.app.fragments.homepage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.data.Car
import com.example.app.databinding.ItemBinding
import kotlin.collections.get

class CarAdapter(private val cars: MutableList<Car>):
    RecyclerView.Adapter<CarAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val type: TextView = binding.twType
    }



    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.type.text = cars[position].type
    }

    override fun getItemCount() = cars.size
}