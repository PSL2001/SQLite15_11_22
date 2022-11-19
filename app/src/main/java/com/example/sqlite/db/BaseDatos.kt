package com.example.sqlite.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
}