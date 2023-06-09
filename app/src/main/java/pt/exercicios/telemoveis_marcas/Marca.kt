package pt.exercicios.telemoveis_marcas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import java.io.Serializable

data class Marca(
    var nome_marca: String,
    var idMarca: Long = -1
) : Serializable {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaMarca.CAMPO_NOME, nome_marca)

        return valores
    }

    companion object{

        fun fromCursor(cursor : Cursor) : Marca{
            val posId = cursor.getColumnIndex(BaseColumns._ID)
            val posMarca = cursor.getColumnIndex(TabelaMarca.CAMPO_NOME)

            val id = cursor.getLong(posId)
            val nome = cursor.getString(posMarca)

            return Marca(nome,id)
        }
    }
}

