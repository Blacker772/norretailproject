package com.example.testapp.ui.menu.viewpager.pages.route.details.create_order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.testapp.data.models.pages.ProductModel
import com.example.testapp.databinding.ItemCreateBinding

class CreateOrderAdapter: Adapter<CreateOrderAdapter.CreateViewHolder>() {

    private val listOfRoutes = mutableListOf<ProductModel>()
    fun updateRoutesList(newList: List<ProductModel>){
        listOfRoutes.clear()
        listOfRoutes.addAll(newList)
        notifyDataSetChanged()
    }

    private var onItemClick: ((product: ProductModel) -> Unit)? = null
    fun onItemClickListener(onItemClick: (product: ProductModel) -> Unit) {
        this.onItemClick = onItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCreateBinding.inflate(inflater, parent, false)
        return CreateViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfRoutes.size
    }

    override fun onBindViewHolder(holder: CreateViewHolder, position: Int) {
        holder.bind(listOfRoutes[position], onItemClick)
    }

    inner class CreateViewHolder(private val binding: ItemCreateBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ProductModel, onItemClick: ((news: ProductModel) -> Unit)?){
            binding.apply {
                tvProductName.text = data.name
                tvProductArticles.text = "Артикул: " + data.articles.toString()
                btnPrice.text = data.price.toString() + " руб"
                tvProductStock.text = "На складе: " + data.stock.toString()
                cardView.setOnClickListener {
                    onItemClick?.invoke(data)
                }
            }
        }
    }
}