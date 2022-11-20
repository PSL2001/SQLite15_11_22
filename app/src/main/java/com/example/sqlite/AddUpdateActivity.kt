package com.example.sqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqlite.databinding.ActivityAddUpdateBinding
import com.example.sqlite.db.BaseDatos
import com.example.sqlite.models.Articulo

class AddUpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddUpdateBinding
    lateinit var conexion: BaseDatos
    var nombre = ""
    var precio = 0.0f
    var stock = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        setListeners()
    }

    private fun setListeners() {
        binding.btnVolver.setOnClickListener {
            finish()
        }
        binding.btnGuardar.setOnClickListener {
            guardarDatos()
        }
    }

    private fun guardarDatos() {
        nombre = binding.etNombre.text.toString().trim()
        precio = binding.etPrecio.text.toString().toFloat()
        stock = binding.etPrecio.text.toString().toInt()

        if (nombre.isEmpty()) {
            binding.etNombre.setError("Este campo no puede estar vacio!")
            return
        }

        if (precio < 0.0f) {
            binding.etPrecio.setError("Este campo debe ser mayor que 0!")
            return
        }

        if (stock < 0) {
            binding.etStock.setError("Este campo debe ser mayor que 0!")
            return
        }

        //Comprobamos que nombre no exista
        val articulo = Articulo(1, nombre, precio, stock)
        if (conexion.crear(articulo) > -1) {
            finish()
        } else {
            Toast.makeText(this, "No se ha podido guardar el registro", Toast.LENGTH_SHORT).show()
        }
    }
}