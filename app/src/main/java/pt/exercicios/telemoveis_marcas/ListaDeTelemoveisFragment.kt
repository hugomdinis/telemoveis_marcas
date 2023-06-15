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
import pt.exercicios.telemoveis_marcas.databinding.FragmentListaDeTelemoveisBinding


private const val ID_LOADER_TELEMOVEIS = 0

class ListaDeTelemoveisFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaDeTelemoveisBinding? = null
    //

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var  telemovelSelecionado : Telemovel? = null
        set(value) {
            field = value

            val mostrarEliminarAlterar = (value != null)

            val activity = activity as MainActivity
            activity.mostraBotaoMenu(R.id.action_editar, mostrarEliminarAlterar)
            activity.mostraBotaoMenu(R.id.action_eliminar, mostrarEliminarAlterar)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListaDeTelemoveisBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private var adapterTelemoveis: AdapterTelemoveis? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterTelemoveis = AdapterTelemoveis(this)
        binding.recyclerviewTelemoveis.adapter = adapterTelemoveis
        binding.recyclerviewTelemoveis.layoutManager =
            LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TELEMOVEIS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_lista_telemoveis
    }

    companion object {
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        return CursorLoader(
            requireContext(),
            TelemoveisContentProvider.ENDERECO_TELEMOVEIS,
            TabelaTelemoveis.TODOS_OS_CAMPOS,
            null, null,
            TabelaTelemoveis.CAMPO_MODELO
        )
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        adapterTelemoveis!!.cursor = null
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterTelemoveis!!.cursor = data
    }

    fun processaOpcaoMenu(item: MenuItem) : Boolean {
        return when (item.itemId) {
            R.id.action_adicionar -> {
                adicionarTelemovel()
                true
            }
            R.id.action_editar -> {
                editarTelemovel()
                true
            }
            R.id.action_eliminar -> {
                eliminarTelemovel()
                true
            }
            else -> false
        }
    }

    private fun eliminarTelemovel() {
        val acao = ListaDeTelemoveisFragmentDirections.actionListaDeTelemoveisFragmentToEliminarTelemovelFragment(telemovelSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun editarTelemovel() {
        val acao = ListaDeTelemoveisFragmentDirections.actionListaDeTelemoveisFragmentToEditarTelemovelFragment(telemovelSelecionado!!)
        findNavController().navigate(acao)
    }

    private fun adicionarTelemovel() {
        findNavController().navigate(R.id.action_ListaDeTelemoveisFragment_to_editarTelemovelFragment)
    }

}