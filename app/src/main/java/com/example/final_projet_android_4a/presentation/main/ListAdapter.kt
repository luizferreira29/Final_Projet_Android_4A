package com.example.final_projet_android_4a.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.final_projet_android_4a.data.local.models.Beer

class ListAdapter(private val list: List<Beer>)
    : RecyclerView.Adapter<ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ListViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val beer: Beer = list[position]
        holder.bind(beer)
    }

    override fun getItemCount(): Int = list.size

}