package pt.exercicios.telemoveis_marcas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaMarca (db:SQLiteDatabase) : TabelaBD(db, NOME_TABELA){
    override fun cria(){
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL)")
    }


    companion object{

        const val NOME_TABELA = "marcas"
        const val CAMPO_ID = "${NOME_TABELA}.${BaseColumns._ID}"
        const val CAMPO_NOME = "nome"

        val TODOS_OS_CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME)
    }
}