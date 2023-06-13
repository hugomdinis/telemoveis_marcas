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
        telemovel.idTelemovel = TabelaTelemoveis(bd).insere(telemovel.toContentValues())
        assertNotEquals(-1, telemovel.idTelemovel)
    }

    private fun insereMarca(bd: SQLiteDatabase, marca: Marca) {
        marca.idMarca = TabelaMarca(bd).insere(marca.toContentValues())
        assertNotEquals(-1, marca.idMarca)
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
        //getAppContext().deleteDatabase(BdTelemoveisOpenHelper.NOME_BASE_DADOS)
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

        val telemovel = Telemovel ("GALAXY A14", "Como Novo","2019",marca)
        insereTelemovel(bd, telemovel)
    }

    @Test
    fun consequeLerTelemoveis(){
        val bd = getWritableDatabase()

        val marca = Marca("Samsung")
        insereMarca(bd, marca)

        val telemovelMotorola = Telemovel("b12", "Usado","2007",marca)
        insereTelemovel(bd, telemovelMotorola)

        val telemovelIphone = Telemovel("14", "Novo","2019", marca)
        insereTelemovel(bd, telemovelIphone)

        val tabelaTelemovel = TabelaTelemoveis(bd)
        val cursor = tabelaTelemovel.consulta(TabelaTelemoveis.TODOS_OS_CAMPOS,"${TabelaTelemoveis.CAMPO_ID}=?", arrayOf(telemovelIphone.idTelemovel.toString()),
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

        val tabelaMarca = TabelaMarca(bd)

        val cursor = tabelaMarca.consulta(TabelaMarca.TODOS_OS_CAMPOS, "${BaseColumns._ID}=?",arrayOf(marca1.idMarca.toString()),
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

    @Test
    fun consegueAlterarMarca(){
        val bd = getWritableDatabase()

        val marca = Marca("...")
        insereMarca(bd, marca)

        marca.nome_marca = "POCO"

        val registosAlteradas = TabelaMarca(bd).altera(marca.toContentValues(), "${BaseColumns._ID}=?",arrayOf(marca.idMarca.toString()))

        assertEquals(1, registosAlteradas)
    }

    @Test
    fun consegueAlterarTelemoveis(){
        val bd = getWritableDatabase()

        val marca = Marca("ZET")
        insereMarca(bd, marca)

        val marcaVivo = Marca("VIVO")
        insereMarca(bd, marcaVivo)

        val telemovel = Telemovel("...", "...","2007",marca)
        insereTelemovel(bd, telemovel)

        telemovel.marca = marcaVivo
        telemovel.modelo = "z3"
        telemovel.ano = "2009"
        telemovel.informacao = "Usado"

        val resgistosAlterados = TabelaTelemoveis(bd).altera(
            telemovel.toContentValues(),
            "${BaseColumns._ID}=?",
            arrayOf(telemovel.idTelemovel.toString())
        )

        assertEquals(1, resgistosAlterados)


    }

    @Test
    fun consegueApagarMarca(){
        val bd = getWritableDatabase()

        val marca = Marca("...")
        insereMarca(bd, marca)

        val registosEliminados = TabelaMarca(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(marca.idMarca.toString()))

        assertEquals(1, registosEliminados)
    }

    @Test
    fun consegueApagarTelemoveis(){
        val bd = getWritableDatabase()

        val marca = Marca("ZET")
        insereMarca(bd, marca)

        val telemovel = Telemovel(".", "...","2007",marca)
        insereTelemovel(bd, telemovel)

        val resgistosEliminados = TabelaTelemoveis(bd).elimina(
            "${BaseColumns._ID}=?",
            arrayOf(telemovel.idTelemovel.toString())
        )

        assertEquals(1, resgistosEliminados)
    }

    fun useAppContext(){
        val appContext = getAppContext()
        assertEquals("com.exercicios.telemoveis_marcas", appContext.packageName)
    }
}