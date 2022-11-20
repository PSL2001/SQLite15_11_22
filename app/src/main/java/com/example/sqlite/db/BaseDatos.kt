package com.example.sqlite.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlite.models.Articulo

class BaseDatos(c: Context): SQLiteOpenHelper(c, DATABASE, null, VERSION) {
    companion object {
        const val VERSION = 1
        const val DATABASE = "articulos"
        const val TABLE = "articulos"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val q = "CREATE TABLE $TABLE(" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT , " +
                "nombre TEXT NOT NULL UNIQUE, " +
                "precio FLOAT NOT NULL, " +
                "stock INTEGER)"
        p0?.execSQL(q)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val q = "DROP TABLE IF EXISTS $TABLE"
        p0?.execSQL(q)
        onCreate(p0)
    }

    //Crear un registro en la base de datos
    fun crear(articulo: Articulo) : Long {
        val conexion = this.writableDatabase
        val valores = ContentValues().apply {
            put("NOMBRE", articulo.nombre)
            put("PRECIO", articulo.precio)
            put("STOCK", articulo.stock)
        }

        val cod = conexion.insert(TABLE, null, valores)
        conexion.close()

        //Devuelve -1 si ha ido mal, usaremos esto para decir al usuario si ha habido un error
        return cod
    }

    @SuppressLint("Range")
    fun readAll(): MutableList<Articulo> {
        val lista = mutableListOf<Articulo>()
        val conexion = this.readableDatabase
        val q = "SELECT * FROM $TABLE ORDER BY nombre"

        try {
            val cursor = conexion.rawQuery(q, null)
            if (cursor.moveToFirst()) {
                do {
                    val articulo = Articulo(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getFloat(cursor.getColumnIndex("precio")),
                        cursor.getInt(cursor.getColumnIndex("stock"))
                    )
                    lista.add(articulo)
                } while (cursor.moveToNext())
                cursor.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        conexion.close()
        return lista
    }

    fun existeNombre(nom: String): Boolean {
        val q = "SELECT id FROM $TABLE where nombre = '$nom'"
        val conexion = this.readableDatabase
        var filas = 0
        try {
            //Abre el cursor
            val cursor = conexion.rawQuery(q, null)
            //Cojemos las filas
            filas = cursor.count
            //Cerramos el cursor
            cursor.close()
        }catch (e: Exception) {
            e.printStackTrace()
        }
        conexion.close()

        return filas != 0
    }

    fun borrar(id: Int) {
        val q = "DELETE FROM $TABLE WHERE id = '$id'"
        val conexion = this.writableDatabase
        conexion.execSQL(q)
        conexion.close()
    }
}