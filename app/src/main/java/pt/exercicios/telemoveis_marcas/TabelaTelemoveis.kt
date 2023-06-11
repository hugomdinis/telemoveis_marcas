package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteQueryBuilder
import android.provider.BaseColumns

class TabelaTelemoveis(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {

    override fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA ($CHAVE_TABELA,$CAMPO_MODELO TEXT NOT NULL, $CAMPO_INFORMACAO TEXT NOT NULL, $CAMPO_ANO TEXT NOT NULL, $CAMPO_FK_MARCA INTEGER NOT NULL, FOREIGN KEY($CAMPO_FK_MARCA) REFERENCES ${TabelaMarca.NOME_TABELA}(${BaseColumns._ID}) ON DELETE RESTRICT)")
    }

    override fun consulta(
        colunas: Array<String>,
        selecao: String?,
        argsSelecao: Array<String>?,
        groupby: String?,
        having: String?,
        orderby: String?
    ): Cursor {
        val sql = SQLiteQueryBuilder()
        sql.tables = "$NOME_TABELA INNER JOIN ${TabelaMarca.NOME_TABELA} ON ${TabelaMarca.CAMPO_ID} = $CAMPO_FK_MARCA"
        return sql.query(db, colunas, selecao, argsSelecao, groupby, having, orderby)
    }

    companion object{
        const val NOME_TABELA = "Telemoveis"

        const val CAMPO_ID = "$NOME_TABELA.${BaseColumns._ID}"
        const val CAMPO_MODELO ="modelo"
        const val CAMPO_INFORMACAO = "informacao"
        const val CAMPO_ANO = "ano"
        const val CAMPO_FK_MARCA = "id_marca"
        const val CAMPO_DESC_MARCA = TabelaMarca.CAMPO_NOME;

        val TODOS_OS_CAMPOS = arrayOf(CAMPO_ID, CAMPO_MODELO, CAMPO_INFORMACAO, CAMPO_ANO, CAMPO_FK_MARCA, CAMPO_DESC_MARCA)
    }
}
