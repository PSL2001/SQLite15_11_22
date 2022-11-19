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

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}