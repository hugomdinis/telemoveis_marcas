package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import pt.exercicios.telemoveis_marcas.databinding.FragmentPesquisaBinding
private const val ID_LOADER_TELEMOVEIS_PESQUISA = 0

class PesquisaFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {

    private var _binding: FragmentPesquisaBinding? = null
    private val binding get() = _binding!!
    private var adapterTelemoveis: AdapterPesquisar? = null

    var telemovelSelecionado: Telemovel? = null
        set(value) {
            field = value
        }

    var textPesquisa: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPesquisaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapterTelemoveis = AdapterPesquisar(this)
        binding.RecyclerViewPesquisa.adapter = adapterTelemoveis
        binding.RecyclerViewPesquisa.layoutManager = LinearLayoutManager(requireContext())

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_TELEMOVEIS_PESQUISA, null, this)

        textPesquisa = binding.TextInputEditTextPesquisar.text.toString()

        binding.TextInputEditTextPesquisar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Nenhuma ação necessária antes da alteração do texto
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Atualizar o texto de pesquisa sempre que houver uma alteração no TextInput
                textPesquisa = s.toString()
                restartLoader()
            }

            override fun afterTextChanged(s: Editable?) {
                // Nenhuma ação necessária após a alteração do texto
            }
        })

    }
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        val selection = "${TabelaTelemoveis.CAMPO_MODELO} LIKE ?"
        val selectionArgs = arrayOf("%$textPesquisa%")
        return CursorLoader(
            requireContext(),
            TelemoveisContentProvider.ENDERECO_TELEMOVEIS,
            TabelaTelemoveis.TODOS_OS_CAMPOS,
            selection,
            selectionArgs,
            TabelaTelemoveis.CAMPO_MODELO
        )

    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        if (adapterTelemoveis != null) {
            adapterTelemoveis!!.cursor = null
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        adapterTelemoveis!!.cursor = data
    }

    private fun restartLoader() {
        LoaderManager.getInstance(this).restartLoader(ID_LOADER_TELEMOVEIS_PESQUISA, null, this)
    }

}