package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.CursorLoader
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.exercicios.telemoveis_marcas.databinding.FragmentListaDeTelemoveisBinding


private const val ID_LOADER_TELEMOVEIS = 0

class ListaDeTelemoveisFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentListaDeTelemoveisBinding? = null
    //

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        binding.recyclerviewTelemoveis.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TELEMOVEIS, null, this)
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

}