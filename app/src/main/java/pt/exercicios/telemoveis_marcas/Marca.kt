package pt.exercicios.telemoveis_marcas

import android.content.ContentValues

data class Marca(
    var nome: String,
    var id: Long = -1
) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaMarca.CAMPO_NOME, nome)

        return valores
    }
}

