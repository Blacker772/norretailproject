package com.example.testapp.ui.menu.viewpager.pages.route.points

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.example.testapp.databinding.ItemShopBinding

class PointAdapter: Adapter<PointAdapter.PointViewHolder>() {

    private val listOfPoints = mutableListOf<Shop>()
    fun updateRoutesList(newList: List<Shop>){
        listOfPoints.clear()
        listOfPoints.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PointViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemShopBinding.inflate(inflater, parent, false)
        return PointViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfPoints.size
    }

    override fun onBindViewHolder(holder: PointViewHolder, position: Int) {
        holder.bind(listOfPoints[position])
    }

    inner class PointViewHolder(private val binding: ItemShopBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Shop){
            binding.shopNameTextView.text = data.name
            binding.shopAddressTextView.text = data.address
            binding.shopIconImageView.load(data.icon)
        }
    }
}