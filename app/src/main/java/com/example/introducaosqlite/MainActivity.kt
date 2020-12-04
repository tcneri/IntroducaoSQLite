package com.example.introducaosqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private lateinit var dataBaseHandler:DataBaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dataBaseHandler = DataBaseHandler(this)
//
//        val gasto = Gasto(1,"Comida", 10.0)
//        val res = dataBaseHandler.addGasto(gasto)
//
//        Log.i("MainActivity", res.toString() )

        val listGasto = dataBaseHandler.getAllGastos()
        listGasto.forEach{
            Log.i("MainActivity", it.toString())
        }
    }
}