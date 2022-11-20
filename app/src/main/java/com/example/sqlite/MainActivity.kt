package com.example.sqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sqlite.adapter.ArticulosAdapter
import com.example.sqlite.databinding.ActivityMainBinding
import com.example.sqlite.db.BaseDatos
import com.example.sqlite.models.Articulo

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var conexion: BaseDatos
    lateinit var adapter: ArticulosAdapter
    var lista = mutableListOf<Articulo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Creamos la conexion
        conexion = BaseDatos(this)
        //Seteamos el recycler
        setRecycler()
        //Seteamos los listeners
        setListeners()
    }

    private fun setListeners() {
        binding.fbtnAdd.setOnClickListener {
            startActivity(Intent(this, AddUpdateActivity::class.java))
        }
    }

    private fun setRecycler() {
        lista = conexion.readAll()
        binding.tvVacio.visibility = View.INVISIBLE
        if (lista.size == 0) {
            binding.tvVacio.visibility = View.VISIBLE
            return
        }
        val layoutManager = LinearLayoutManager(this)
        binding.rcAticulos.layoutManager = layoutManager
        adapter = ArticulosAdapter(lista, {onItemDelete(it)})
        binding.rcAticulos.adapter = adapter
    }

    private fun onItemDelete(position: Int) {
        val usuario = lista[position]
        //Borramos de la lista
        conexion.borrar(usuario.id)
        lista.removeAt(position)
        if (lista.size == 0) {
            binding.tvVacio.visibility = View.VISIBLE
        }
        adapter.notifyItemRemoved(position)
    }

    override fun onResume() {
        super.onResume()
        setRecycler()
    }
}