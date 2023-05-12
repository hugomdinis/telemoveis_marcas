package pt.exercicios.telemoveis_marcas

import android.content.ContentValues

data class Telemovel(
    var nome: String,
    var informacao: String?,
    var ano: Long,
    var id_marca: String? = null,
    var id : Long = -1
){
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaTelemoveis.CAMPO_NOME, nome)
        valores.put(TabelaTelemoveis.CAMPO_INFORMACAO, informacao)
        valores.put(TabelaTelemoveis.CAMPO_ANO, ano)
        valores.put(TabelaTelemoveis.CAMPO_FK_MARCA, id_marca)

        return valores
    }
}