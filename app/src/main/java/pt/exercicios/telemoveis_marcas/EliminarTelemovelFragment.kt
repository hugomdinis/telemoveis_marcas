package pt.exercicios.telemoveis_marcas

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.exercicios.telemoveis_marcas.databinding.FragmentEliminarTelemovelBinding

class EliminarTelemovelFragment : Fragment() {
    private lateinit var telemovel: Telemovel
    private var _binding: FragmentEliminarTelemovelBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentEliminarTelemovelBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        telemovel = EliminarTelemovelFragmentArgs.fromBundle(requireArguments()).telemovel

        binding.textViewMarcaEliminar.text = telemovel.marca.nome_marca
        binding.textViewModeloEliminar.text = telemovel.modelo
        binding.textViewIformacaoEliminar.text = telemovel.informacao
        binding.textViewAnoEliminar.text = telemovel.ano

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_eliminar -> {
                eliminar()
                true
            }
            R.id.action_cancelar -> {
                voltaListaTelemoveis()
                true
            }
            else -> false
        }
    }

    private fun voltaListaTelemoveis() {
        findNavController().navigate(R.id.action_eliminarTelemovelFragment_to_ListaDeTelemoveisFragment)
    }

    private fun eliminar() {
        val enderecoTelemovel = Uri.withAppendedPath(TelemoveisContentProvider.ENDERECO_TELEMOVEIS, telemovel.toString())
        val numTelemovelSelecionado = requireActivity().contentResolver.delete(enderecoTelemovel,null,null)


        if (numTelemovelSelecionado == 1){
            Toast.makeText(requireContext(), getString(R.string.telemovel_eliminado_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaTelemoveis()
        }else{
            Snackbar.make(binding.textViewMarcaEliminar, getString(R.string.erro_eliminar_telemovel), Snackbar.LENGTH_INDEFINITE)
        }
    }
}