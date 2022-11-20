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
    var editar = false
    var id = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        conexion = BaseDatos(this)
        cogerDatos()
        setListeners()
    }

    private fun cogerDatos() {
        val datos = intent.extras
        if (datos != null) {
            editar = true
            binding.btnGuardar.setText(R.string.btnEditar)
            val articulo = datos.getSerializable("ARTICULO") as Articulo
            id = articulo.id
            binding.etNombre.setText(articulo.nombre)
            binding.etStock.setText(articulo.stock.toString())
            binding.etPrecio.setText(articulo.precio.toString())

        }
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
        stock = binding.etStock.text.toString().toInt()

        if (nombre.isEmpty()) {
            binding.etNombre.setError("Este campo no puede estar vacio!")
            binding.etNombre.requestFocus()
            return
        }

        if (precio < 0.0f) {
            binding.etPrecio.setError("Este campo debe ser mayor que 0!")
            binding.etPrecio.requestFocus()
            return
        }

        if (stock < 0) {
            binding.etStock.setError("Este campo debe ser mayor que 0!")
            binding.etStock.requestFocus()
            return
        }

        //Comprobamos que nombre no exista
        if (conexion.existeNombre(nombre, id)) {
            binding.etNombre.setError("Este nombre ya existe!")
            binding.etNombre.requestFocus()
            return
        }
        if (!editar) {
            val articulo = Articulo(1, nombre, precio, stock)
            if (conexion.crear(articulo) > -1) {
                finish()
            } else {
                Toast.makeText(this, "No se ha podido guardar el registro", Toast.LENGTH_SHORT).show()
            }
        } else {
            val articulo = Articulo(id, nombre, precio, stock)
            if (conexion.update(articulo) > -1) {
                finish()
            } else {
                Toast.makeText(this, "No se ha podido actualizar el registro", Toast.LENGTH_SHORT).show()
            }
        }
    }
}