package com.example.testapp.ui.menu.viewpager.pages.route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.database.entity.Clients
import com.example.testapp.databinding.MainItemBinding

class RouteAdapter : ListAdapter<Clients, RouteAdapter.RouteViewHolder>(DIFF_UTIL) {

    var originalList = listOf<Clients>()
    private var onItemClick: ((client: Clients) -> Unit)? = null
    fun onItemClickListener(onItemClick: (client: Clients) -> Unit) {
        this.onItemClick = onItemClick
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            originalList

        } else {
            originalList.filter {
                it.name.contains(query, ignoreCase = true)
                        || it.nUser.contains(query, ignoreCase = true)
                        || it.deliveryAddress.contains(query, ignoreCase = true)
            }
        }
        submitList(filteredList) // передаем отфильтрованный список, а не строку
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainItemBinding.inflate(inflater, parent, false)
        return RouteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RouteViewHolder, position: Int) {
        holder.bind(currentList[position], onItemClick)
    }

    inner class RouteViewHolder(private val binding: MainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Clients, onItemClick: ((client: Clients) -> Unit)?) {
            binding.apply {
                tvCode.text = data.nUser
                tvName.text = data.name
                tvAddress.text = data.deliveryAddress
                cardMarket.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Clients>() {
            override fun areItemsTheSame(
                oldItem: Clients,
                newItem: Clients,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Clients,
                newItem: Clients,
            ): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.inn == newItem.inn &&
                        oldItem.kpp == newItem.kpp
            }
        }
    }
}