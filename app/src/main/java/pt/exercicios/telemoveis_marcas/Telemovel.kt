package pt.exercicios.telemoveis_marcas

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns

data class Telemovel(
    var modelo: String?,
    var informacao: String?,
    var ano: Int,
    var id_marca: Long = -1,
    var id: Long = -1
){
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaTelemoveis.CAMPO_MODELO, modelo)
        valores.put(TabelaTelemoveis.CAMPO_INFORMACAO, informacao)
        valores.put(TabelaTelemoveis.CAMPO_ANO, ano)
        valores.put(TabelaTelemoveis.CAMPO_FK_MARCA, id_marca)

        return valores
    }
    companion object{
        fun fromCursor(cursor: Cursor): Telemovel{
            val posicaoId = cursor.getColumnIndex(BaseColumns._ID)
            val posicaoNome = cursor.getColumnIndex(TabelaTelemoveis.CAMPO_MODELO)
            val posicaoAno = cursor.getColumnIndex(TabelaTelemoveis.CAMPO_ANO)
            val posicaoInf = cursor.getColumnIndex(TabelaTelemoveis.CAMPO_INFORMACAO)
            val posMarcaFK = cursor.getColumnIndex(TabelaTelemoveis.CAMPO_FK_MARCA)

            val id = cursor.getLong(posicaoId)
            val nome = cursor.getString(posicaoNome)
            val ano = cursor.getInt(posicaoAno)
            val informacao = cursor.getString(posicaoInf)
            val marcaID = cursor.getLong(posMarcaFK)

            return Telemovel(nome, informacao, ano, marcaID, id)
        }

    }
}