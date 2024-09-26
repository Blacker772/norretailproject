package com.example.testapp.ui.menu.viewpager.pages.route

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.data.pages.ClientModel
import com.example.testapp.databinding.MainItemBinding

class RouteAdapter : ListAdapter<ClientModel, RouteAdapter.RouteViewHolder>(DIFF_UTIL) {

    private var onItemClick: ((client: ClientModel) -> Unit)? = null
    fun onItemClickListener(onItemClick: (client: ClientModel) -> Unit) {
        this.onItemClick = onItemClick
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
        fun bind(data: ClientModel, onItemClick: ((client: ClientModel) -> Unit)?) {
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
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ClientModel>() {
            override fun areItemsTheSame(
                oldItem: ClientModel,
                newItem: ClientModel,
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ClientModel,
                newItem: ClientModel,
            ): Boolean {
                return oldItem.name == newItem.name &&
                        oldItem.inn == newItem.inn &&
                        oldItem.kpp == newItem.kpp
            }
        }
    }
}