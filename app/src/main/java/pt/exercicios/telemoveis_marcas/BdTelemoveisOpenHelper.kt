package pt.exercicios.telemoveis_marcas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val NOME_BASE_DADOS = "Telemoveis:db"
private const val VERSÃO_BASE_DADOS = 1


class BdTelemoveisOpenHelper(
    context: Context?,
    name: String?,
    version: Int
) : SQLiteOpenHelper(context, NOME_BASE_DADOS, null, VERSÃO_BASE_DADOS) {
    //called when de database is created for the first time. This is where the creation of tables and
    //and the initial population of the tables should happen.

    override fun onCreate(db: SQLiteDatabase?) {

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}