package pt.exercicios.telemoveis_marcas

import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class TabelaTelemoveis(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA,$CAMPO_MODELO TEXT NOT NULL, $CAMPO_INFORMACAO TEXT NOT NULL, $CAMPO_ANO INT NOT NULL, $CAMPO_FK_MARCA INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_MARCA) REFERENCES ${TabelaMarca.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    companion object{
        const val NOME_TABELA = "Telemoveis"
        const val CAMPO_MODELO ="modelo"
        const val CAMPO_INFORMACAO = "informacao"
        const val CAMPO_ANO = "ano"
        const val CAMPO_FK_MARCA = "id_marca"

        val TODOS_OS_CAMPOS = arrayOf(BaseColumns._ID, TabelaMarca.CAMPO_NOME)
    }
}
