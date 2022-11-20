package com.example.sqlite.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.R
import com.example.sqlite.models.Articulo

class ArticulosAdapter(private val lista:MutableList<Articulo>): RecyclerView.Adapter<ArticulosViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticulosViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.article_layout, parent, false)
        return ArticulosViewHolder(v)
    }

    override fun onBindViewHolder(holder: ArticulosViewHolder, position: Int) {
        holder.render(lista[position])
    }

    override fun getItemCount(): Int {
        return lista.size
    }

}