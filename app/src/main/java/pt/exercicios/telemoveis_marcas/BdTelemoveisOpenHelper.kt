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
    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
}