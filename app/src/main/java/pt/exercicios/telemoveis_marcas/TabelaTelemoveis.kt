package pt.exercicios.telemoveis_marcas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaTelemoveis(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA, nome TEXT NOT NULL, informacao TEXT NOT NULL, ano DATE NOT NULL, id_marca INTEGER NOT NULL), FOREIGN KEY(id_marca) REFERENCES ${TabelaMarca.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT")
    }

    companion object{
        const val NOME_TABELA = "Telemoveis"
    }
}
