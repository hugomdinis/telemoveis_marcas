package pt.exercicios.telemoveis_marcas

import android.content.ContentValues

data class Telemovel(
    var nome_telemovel: String?,
    var informacao: String?,
    var ano: Int,
    var id_marca: Long = -1,
    var id: Long = -1
){
    fun toContentValues() : ContentValues{
        val valores = ContentValues()

        valores.put(TabelaTelemoveis.CAMPO_NOME, nome_telemovel)
        valores.put(TabelaTelemoveis.CAMPO_INFORMACAO, informacao)
        valores.put(TabelaTelemoveis.CAMPO_ANO, ano)
        valores.put(TabelaTelemoveis.CAMPO_FK_MARCA, id_marca)

        return valores
    }
}