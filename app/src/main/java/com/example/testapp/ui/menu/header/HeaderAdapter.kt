package com.example.testapp.ui.menu.header

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.testapp.databinding.ButtonItemBinding

class HeaderAdapter:Adapter<HeaderAdapter.MainViewHolder>() {

    private val listOfAddress = mutableListOf<ButtonModel>()
    fun updateAddressList(newList: List<ButtonModel>){
        listOfAddress.clear()
        listOfAddress.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ButtonItemBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listOfAddress.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(listOfAddress[position])
    }

    inner class MainViewHolder(private val binding: ButtonItemBinding): ViewHolder(binding.root){
        fun bind(data: ButtonModel){
            binding.ivImage.load(data.image)
            binding.tvTex.text = data.text
        }
    }
}