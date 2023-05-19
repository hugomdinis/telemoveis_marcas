package pt.exercicios.telemoveis_marcas

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class TelemoveisContentProvider : ContentProvider(){

    private var bdOpenHelper : BdTelemoveisOpenHelper? = null
    override fun onCreate(): Boolean {
        bdOpenHelper = BdTelemoveisOpenHelper(context)
        return true
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        TODO("Not yet implemented")
    }

    override fun getType(p0: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        TODO("Not yet implemented")
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    companion object{
        private const val AUTORIDADE = "pt.exercicios.telemoveis_marcas"

        const val MARCA = "Marca"
        const val  TELEMOVEIS = "Telemovel"

        private const val URI_MARCA = 100
        private const val URI_TELEMOVEIS = 200

        fun UriMatcher() = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTORIDADE, MARCA, URI_MARCA)
            addURI(AUTORIDADE, TELEMOVEIS, URI_TELEMOVEIS)

        }
    }

}