package pt.exercicios.telemoveis_marcas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaMarca (db:SQLiteDatabase) : TabelaBD(db, NOME_TABELA){
    override fun cria(){
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, $CAMPO_NOME TEXT NOT NULL)")
    }

    companion object{
        const val NOME_TABELA = "Marca"
        const val CAMPO_NOME = "nome"

        val TODOS_OS_CAMPOS = arrayOf(BaseColumns._ID, CAMPO_NOME)
    }
}