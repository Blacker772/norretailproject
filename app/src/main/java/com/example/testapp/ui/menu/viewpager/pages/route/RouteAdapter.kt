package com.example.testapp.ui.menu.viewpager.pages.route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import coil.load
import com.example.testapp.data.pages.RouteModel
import com.example.testapp.databinding.MainItemBinding

class RouteAdapter: Adapter<RouteAdapter.RouteViewHolder>() {

    private val listOfRoutes = mutableListOf<RouteModel>()
    fun updateRoutesList(newList: List<RouteModel>){
        listOfRoutes.clear()
        listOfRoutes.addAll(newList)
        notifyDataSetChanged()
    }

    private var onItemClick: ((news: RouteModel) -> Unit)? = null
    fun onItemClickListener(onItemClick: (news: RouteModel) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainItemBinding.inflate(inflater, parent, false)
        return RouteViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return listOfRoutes.size
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(listOfRoutes[position], onItemClick)
    }

    inner class RouteViewHolder(private val binding: MainItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: RouteModel, onItemClick: ((news: RouteModel) -> Unit)?){
            binding.apply {
                sivIcon.load(data.icon)
                tvCode.text = data.code
                tvName.text = data.name
                tvAddress.text = data.address
                tvPrice.text = data.price
                tvSale.text = data.sale
                cardMarket.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }
}