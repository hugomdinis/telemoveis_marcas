package pt.exercicios.telemoveis_marcas

import android.database.sqlite.SQLiteDatabase

private const val NOME_TABELA = "Télemmoveis"

class TabelaMarca (db:SQLiteDatabase) : TabelaBD(db, NOME_TABELA){
    override fun cria(){
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, nome TEXT NOT NULL)")
    }
}