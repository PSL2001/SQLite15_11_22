package com.example.sqlite.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.sqlite.databinding.ArticleLayoutBinding
import com.example.sqlite.models.Articulo

class ArticulosViewHolder(v: View): RecyclerView.ViewHolder(v) {
    private val binding = ArticleLayoutBinding.bind(v)

    fun render(articulo: Articulo, onItemDelete: (Int) -> Unit, onItemUpdate: (Articulo) -> Unit) {
        binding.tvNombre.text = articulo.nombre
        binding.tvPrecio.text = articulo.precio.toString()
        binding.tvStock.text = articulo.stock.toString()
        binding.btnDelete.setOnClickListener {
            onItemDelete(adapterPosition)
        }
        itemView.setOnClickListener {
            onItemUpdate(articulo)
        }
    }

}
