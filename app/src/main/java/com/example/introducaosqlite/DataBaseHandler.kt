package com.example.introducaosqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DataBaseHandler(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private var DATABASE_NAME = "mywallet"
        //a versao n pode ser 0
        //para atualizar o db é so modificar a version
        private var DATABASE_VERSION = 1

        //nome da tabela e de suas colunas
        private val TABLE_GASTOS = "gastos"
        private val KEY_ID = "id"
        private val KEY_NOME = "nome"
        private val KEY_VALOR = "valor"


    }

    //Método de criação do db
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_GASTOS = ("CREATE TABLE $TABLE_GASTOS(" +
                "$KEY_ID INTEGER PRIMARY KEY," +
                "$KEY_NOME TEXT," +
                "$KEY_VALOR REAL" +
                ")")
        db?.execSQL(CREATE_TABLE_GASTOS)
    }

    //Método de atualização do db
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_GASTOS")
    }

    fun addGasto(gasto:Gasto):Long{
        val db = this.writableDatabase

        //Mapeamento de key e value
        val content = ContentValues()
        content.put(KEY_ID, gasto.id)
        content.put(KEY_NOME, gasto.nome)
        content.put(KEY_VALOR, gasto.valor)

        val res = db.insert(TABLE_GASTOS, null, content)
        db.close()

        return  res
    }

    fun getAllGastos():List<Gasto>{
        val listGasto = ArrayList<Gasto>()
        val query = "SELECT * FROM $TABLE_GASTOS"
        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            //pegando os dados de acordo com a query
            cursor = db.rawQuery(query,null)
            //se tiver elemento ele percorre
            if(cursor.moveToFirst()){
                do {

                    //percorrendo as colunas e add na lista
                    val id = cursor.getInt(cursor.getColumnIndex("id"))
                    val nome = cursor.getString(cursor.getColumnIndex("nome"))
                    val valor = cursor.getDouble(cursor.getColumnIndex("valor"))
                    listGasto.add(Gasto(id, nome, valor))

                }while (cursor.moveToNext())
            }
        }catch (e:Exception){
            Log.e("ERRO", e.toString())
        }

        return listGasto
    }
}