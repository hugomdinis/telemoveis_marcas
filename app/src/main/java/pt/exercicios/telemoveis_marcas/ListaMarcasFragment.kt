package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.exercicios.telemoveis_marcas.databinding.FragmentListaMarcasBinding

private const val ID_LOADER_MARCAS = 0
class ListaMarcasFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaMarcasBinding? = null

    private val binding get() = _binding!!

    var marcaSelecionada : Marca? = null
        set(value) {
            field = value

            val mostrarElimnarAlterar = (value != null)
            val activity = activity as MainActivity
            activity.mostraBotaoMenu(R.id.action_editar,mostrarElimnarAlterar)
            activity.mostraBotaoMenu(R.id.action_eliminar,mostrarElimnarAlterar)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaMarcasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterMarcas: AdapterMarcas? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        adapterMarcas = AdapterMarcas(this)
        binding.RecyclerViewMarcas.adapter = adapterMarcas
        binding.RecyclerViewMarcas.layoutManager =
            LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_MARCAS,null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_telemoveis
    }


    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            TelemoveisContentProvider.ENDERECO_MARCA,
            TabelaMarca.TODOS_OS_CAMPOS,
            null, null,
            TabelaMarca.CAMPO_NOME
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterMarcas!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterMarcas!!.cursor = data
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionaMarca()
                true
            }
            R.id.action_editar -> {
                editarMarca()
                true
            }
            R.id.action_eliminar -> {
                eliminarMarca()
                true
            }
            else -> false
        }
    }

    private fun eliminarMarca() {
        val acao = ListaMarcasFragmentDirections.actionListaMarcasFragmentToEliminarMarcaFragment(marcaSelecionada!!)
        findNavController().navigate(acao)
    }

    private fun editarMarca() {
       val acao = ListaMarcasFragmentDirections.actionListaMarcasFragmentToEditarMarcaFragment(null)
        findNavController().navigate(acao)
    }

    private fun adicionaMarca() {
        findNavController().navigate(R.id.action_ListaMarcasFragment_to_EditarMarcaFragment)
    }
}