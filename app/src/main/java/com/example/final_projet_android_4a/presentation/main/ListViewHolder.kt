package com.example.final_projet_android_4a.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_projet_android_4a.R
import com.example.final_projet_android_4a.data.local.models.Beer

class ListViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.row_layout, parent, false)) {
    private var mNameView: TextView? = null
    private var mSpeciesView: TextView? = null

    init {
        mNameView= itemView.findViewById(R.id.firstLine)
        mSpeciesView = itemView.findViewById(R.id.secondLine)
    }

    fun bind(beer: Beer) {
        mNameView?.text = beer.name
        mSpeciesView?.text = beer.url
    }

}