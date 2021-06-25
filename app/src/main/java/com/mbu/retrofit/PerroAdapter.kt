package com.mbu.retrofit

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mbu.retrofit.databinding.ItemPerroBinding
import com.squareup.picasso.Picasso

class PerroAdapter(val images: List<String>) : RecyclerView.Adapter<PerroViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerroViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        return PerroViewHolder(layoutInflater.inflate(R.layout.item_perro, parent, false))
    }

    override fun onBindViewHolder(holder: PerroViewHolder, position: Int) {
        val item: String = images[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return images.size
    }

}


class PerroViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPerroBinding.bind(view)

    fun bind(image: String) {
        Picasso.get().load(image).into(binding.fotoIv)
    }

}
