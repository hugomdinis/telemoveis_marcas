package pt.exercicios.telemoveis_marcas

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class BdInstrumentedTest {

    private fun insereTelemovel(
        bd: SQLiteDatabase,
        telemovel: Telemovel
    ) {
        TabelaTelemoveis(bd).insere(telemovel.toContentValues())
        assertNotEquals(-1, telemovel.id)
    }

    private fun insereMarca(bd: SQLiteDatabase, marca: Marca) {
        val id = TabelaMarca(bd).insere(marca.toContentValues())
        assertNotEquals(-1, id)
    }

    private fun getWritableDatabase(): SQLiteDatabase {
        val openHelper = BdTelemoveisOpenHelper(getAppContext())
        val bd = openHelper.writableDatabase
        return bd
    }

    private fun getAppContext(): Context =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun apagaBaseDados(){
        getAppContext().deleteDatabase(BdTelemoveisOpenHelper.NOME_BASE_DADOS)
    }

    @Test
    fun consegueAbrirBaseDados() {
        val openHelper = BdTelemoveisOpenHelper(getAppContext())
        val bd = openHelper.readableDatabase
        assert(bd.isOpen)
    }

    @Test
    fun consegueInserirMarcas(){
        val bd = getWritableDatabase()

        val marca = Marca("Samsung")
        insereMarca(bd, marca)
    }

    @Test
    fun consegueInserirTelemoveis(){
        val bd = getWritableDatabase()

        val marca = Marca("Samsung")
        insereMarca(bd, marca)

        val telemovel = Telemovel ("GALAXY A14", "Como Novo",2019,marca.id)
        insereTelemovel(bd, telemovel)
    }

    @Test
    fun consequeLerTelemoveis(){
        val bd = getWritableDatabase()

        val telemovelMotorola = Telemovel("b12", "Usado",2007,1,1)
        insereTelemovel(bd, telemovelMotorola)

        val telemovelIphone = Telemovel("14", "Novo",2019,2,2)
        insereTelemovel(bd, telemovelIphone)

        val tabelaTelemovel = TabelaTelemoveis(bd)
        val cursor = tabelaTelemovel.consulta(TabelaMarca.TODOS_OS_CAMPOS,"${BaseColumns._ID}=?", arrayOf(telemovelIphone.id.toString()),
            null,null,null)

        assert(cursor.moveToNext())

        val telemovelBD = Telemovel.fromCursor(cursor)

        assertEquals(telemovelIphone, telemovelBD)

        val cursorTodasOsTelemoveis = tabelaTelemovel.consulta(
            TabelaTelemoveis.TODOS_OS_CAMPOS,
            null,null,null,null,
            TabelaTelemoveis.CAMPO_MODELO
        )

        assert(cursorTodasOsTelemoveis.count > 1)
    }

    @Test
    fun consegueLerMarca(){
        val bd = getWritableDatabase()

        val marca = Marca("Iphone")
        insereMarca(bd, marca)

        val marca1 = Marca("Samsung")
        insereMarca(bd, marca1)

        val marca2 = Marca("Xiaomi")
        insereMarca(bd, marca2)

        val tabelaMarca = TabelaMarca(bd)

        val cursor = tabelaMarca.consulta(TabelaMarca.TODOS_OS_CAMPOS, "${BaseColumns._ID}=?",arrayOf(marca.id.toString()),
            null,null,null)

        assert(cursor.moveToNext())

        val marcaBD = Marca.fromCursor(cursor)

        assertEquals(marca1, marcaBD)

        val cursorTodasTelemoveis= tabelaMarca.consulta(
            TabelaMarca.TODOS_OS_CAMPOS,
            null,null, null, null,
            TabelaMarca.CAMPO_NOME
        )

        assert(cursorTodasTelemoveis.count > 1)
    }

    fun useAppContext(){
        val appContext = getAppContext()
        assertEquals("com.exercicios.telemoveis_marcas", appContext.packageName)
    }
}