package pt.exercicios.telemoveis_marcas

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns

class TelemoveisContentProvider : ContentProvider() {

    private var bdOpenHelper: BdTelemoveisOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdTelemoveisOpenHelper(context)
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val bd = bdOpenHelper!!.readableDatabase

        val endereco = UriMatcher().match(uri)

        val tabela = when (endereco) {
            URI_MARCA, URI_MARCA_ID -> TabelaMarca(bd)
            URI_TELEMOVEIS, URI_TELEMOVEL_ID -> TabelaTelemoveis(bd)
            else -> null
        }
        val id = uri.lastPathSegment

        val (selecao, argsSel) = when (endereco) {
            URI_MARCA_ID, URI_TELEMOVEL_ID -> Pair("${BaseColumns._ID}=?",arrayOf(id))
            else -> Pair(selection,selectionArgs)
        }

        return tabela?.consulta(
                projection as Array<String>,
                selecao,
                argsSel as Array<String>?,
                null,
                null,
                sortOrder)

    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val bd = bdOpenHelper!!.readableDatabase

        val endereco = UriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_MARCA -> TabelaMarca(bd)
            URI_TELEMOVEIS -> TabelaTelemoveis(bd)
            else -> return null
        }
        val id = tabela.insere(values!!)
        if (id == -1L){
            return null
        }

        return Uri.withAppendedPath(uri, id.toString())
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = UriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_MARCA_ID -> TabelaMarca(bd)
            URI_TELEMOVEL_ID -> TabelaTelemoveis(bd)
            else -> return 0
        }
        val id = uri.lastPathSegment!!
        return tabela.elimina("${BaseColumns._ID}=?", arrayOf(id))
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        val bd = bdOpenHelper!!.writableDatabase

        val endereco = UriMatcher().match(uri)
        val tabela = when (endereco) {
            URI_MARCA_ID -> TabelaMarca(bd)
            URI_TELEMOVEL_ID -> TabelaTelemoveis(bd)
            else -> return 0
        }
        val id = uri.lastPathSegment!!
        return tabela.altera(values!!, "${BaseColumns._ID}=?", arrayOf(id))
    }

    companion object{
        private const val AUTORIDADE = "pt.exercicios.telemoveis_marcas"

        const val MARCA = "Marca"
        const val  TELEMOVEIS = "Telemovel"

        private const val URI_MARCA = 100
        private const val URI_MARCA_ID = 101
        private const val URI_TELEMOVEIS = 200
        private const val URI_TELEMOVEL_ID = 201


        fun UriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, MARCA, URI_MARCA)
            addURI(AUTORIDADE, "$MARCA/#", URI_MARCA_ID)
            addURI(AUTORIDADE, TELEMOVEIS, URI_TELEMOVEIS)
            addURI(AUTORIDADE, "$TELEMOVEIS/#", URI_TELEMOVEL_ID)


        }
    }

}