package pt.exercicios.telemoveis_marcas


import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import pt.exercicios.telemoveis_marcas.databinding.FragmentEliminarMarcaBinding

class EliminarMarcaFragment : Fragment() {

    private lateinit var marca: Marca
    private var _binding: FragmentEliminarMarcaBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEliminarMarcaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_eliminar

        marca = EliminarMarcaFragmentArgs.fromBundle(requireArguments()).marcas

        binding.textViewMarcaEliminar.text = marca.nome_marca
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
                voltaListaMarcas()
                true
            }
            else -> false
        }
    }

    private fun voltaListaMarcas() {
        findNavController().navigate(R.id.action_eliminarMarcaFragment_to_ListaMarcasFragment)
    }

    private fun eliminar() {
        val enderecoCategorias = Uri.withAppendedPath(TelemoveisContentProvider.ENDERECO_MARCA, marca.idMarca.toString())
        val numCategoriaSelecionadas = requireActivity().contentResolver.delete(enderecoCategorias,null, null)

        if (numCategoriaSelecionadas == 1){
            Toast.makeText(requireContext(), getString(R.string.marca_eliminada_com_sucesso), Toast.LENGTH_LONG).show()
            voltaListaMarcas()
        } else{
            Snackbar.make(binding.textViewMarcaEliminar, getString(R.string.erro_ao_eliminar_marca), Snackbar.LENGTH_INDEFINITE)

        }
    }

}