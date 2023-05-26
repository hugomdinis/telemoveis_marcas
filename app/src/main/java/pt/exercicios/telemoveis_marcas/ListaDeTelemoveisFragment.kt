package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pt.exercicios.telemoveis_marcas.databinding.FragmentListaDeTelemoveisBinding
import pt.exercicios.telemoveis_marcas.databinding.FragmentMenuPrincipalBinding


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
        return inflater.inflate(R.layout.fragment_lista_de_telemoveis, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapterTelemoveis = AdapterTelemoveis()
        binding.recyclerviewTelemoveis.adapter = adapterTelemoveis
        binding.recyclerviewTelemoveis.layoutManager = LinearLayoutManager(requireContext())
    }

    companion object {
    }

    override fun onCreateLoader(p0: Int, p1: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(p0: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(p0: Loader<Cursor>, p1: Cursor?) {
        TODO("Not yet implemented")
    }
}