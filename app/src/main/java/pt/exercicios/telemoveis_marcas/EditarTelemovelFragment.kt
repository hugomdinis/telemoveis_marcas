package pt.exercicios.telemoveis_marcas


import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.exercicios.telemoveis_marcas.databinding.FragmentEditarTelemovelBinding

private const val ID_LOADER_MARCAS = 0

class EditarTelemovelFragment : Fragment(), LoaderManager.LoaderCallbacks<Cursor> {
    private var telemoveis: Telemovel?= null
    private var _binding: FragmentEditarTelemovelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentEditarTelemovelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loader = LoaderManager.getInstance(this)
        loader.initLoader(ID_LOADER_MARCAS, null, this)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar

        val telemovel = EditarTelemovelFragmentArgs.fromBundle(requireArguments()).telemovel

        if (telemovel != null) {
            binding.idTextModelo.setText(telemovel.modelo)
            binding.idTextInformacao.setText(telemovel.informacao)
            binding.idTextAno.setText(telemovel.ano)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_menu_guardar -> {
                guardar()
                true
            }
            R.id.action_menu_cancelar -> {
                voltarListaTelemoveis()
                true
            }
            else -> false
        }
    }

    private fun voltarListaTelemoveis() {
        findNavController().navigate(R.id.action_editarTelemovelFragment_to_ListaDeTelemoveisFragment)
    }

    private fun guardar() {
        val modelo = binding.idTextModelo.text.toString()
        if (modelo.isBlank()){
            binding.idTextModelo.error= "Modelo Obrigatorio"
            binding.idTextModelo.requestFocus()
            return
        }

        val informacao = binding.idTextInformacao.text.toString()
        if (informacao.isBlank()){
            binding.idTextInformacao.error = "Descriçao Obrigatorio"
            binding.idTextInformacao.requestFocus()
            return
        }

        val ano = binding.idTextAno.text.toString()
        if (ano.isBlank()){
            binding.idTextAno.error = "Ano Obrigatorio"
            binding.idTextAno.requestFocus()
            return
        }

        val marca = binding.spinnerMarca.selectedItemId

        if (telemoveis == null){
            val telemovel = Telemovel(
                modelo,
                informacao,
                ano,
                Marca("?",marca)
            )
            insereTelemovel(telemovel)
        }else{
            val telemovel = telemoveis!!
            telemovel.modelo = modelo
            telemovel.informacao = informacao
            telemovel.ano = ano
            telemovel.marca = Marca("?", marca)

            alteraTelemovel(telemovel)
        }
    }

    private fun alteraTelemovel(telemovel: Telemovel) {
        val enderecoTelemovel = Uri.withAppendedPath(TelemoveisContentProvider.ENDERECO_TELEMOVEIS, telemovel.idTelemovel.toString())
        val telemovelAlterados = requireActivity().contentResolver.update(enderecoTelemovel, telemovel.toContentValues(), null, null)

        if (telemovelAlterados == 1){
            Toast.makeText(requireContext(), R.string.telemovel_guardado_com_sucesso, Toast.LENGTH_LONG).show()
            voltarListaTelemoveis()
        }else{
            binding.idTextModelo.error = getString(R.string.nao_foi_possivel_guarda_telemovel)
        }
    }

    private fun insereTelemovel(telemovel: Telemovel) {
        val id = requireActivity().contentResolver.insert(
            TelemoveisContentProvider.ENDERECO_TELEMOVEIS,
            telemovel.toContentValues()
        )

        if (id == null){
            binding.idTextModelo.error=
                getString(R.string.nao_foi_possivel_guarda_telemovel)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.telemovel_guardado_com_sucesso),
            Toast.LENGTH_LONG
        ).show()
        voltarListaTelemoveis()
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
        binding.spinnerMarca.adapter = null
        if (binding !=null){
            binding.spinnerMarca.adapter = null
        }
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        if (data == null) {
            binding.spinnerMarca.adapter = null
            return
        }

        binding.spinnerMarca.adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            data,
            arrayOf(TabelaMarca.CAMPO_NOME),
            intArrayOf(android.R.id.text1),
            0
        )

        mostraMarcaSelcionadoSpinner()
    }
    private fun mostraMarcaSelcionadoSpinner(){
        if (telemoveis == null) return

        val idMarca = telemoveis!!.marca.idMarca

        val ultimaMarca = binding.spinnerMarca.count - 1
        for (i in 0..ultimaMarca){
            if (idMarca == binding.spinnerMarca.getItemIdAtPosition(i)){
                binding.spinnerMarca.setSelection(i)
                return
            }
        }
    }


}