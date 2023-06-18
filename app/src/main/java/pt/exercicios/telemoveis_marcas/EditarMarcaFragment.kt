package pt.exercicios.telemoveis_marcas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import pt.exercicios.telemoveis_marcas.databinding.FragmentEditarMarcaBinding


class EditarMarcaFragment : Fragment() {

    private var _binding: FragmentEditarMarcaBinding? = null

    private val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentEditarMarcaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as MainActivity
        activity.fragment = this
        activity.idMenuAtual = R.menu.menu_guardar_cancelar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun processaOpcaoMenu(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_menu_guardar -> {
                guardarMarca()
                true
            }
            R.id.action_menu_cancelar -> {
                voltarlistaMarcas()
                true
            }
            else -> false
        }
    }

    private fun guardarMarca() {
        val nomeMarca = binding.editTextNomeMarca.toString()
        if (nomeMarca.isBlank()){
            binding.editTextNomeMarca.error = getString(R.string.nomeMarca_obrigatorio)
            binding.editTextNomeMarca.requestFocus()
            return
        }

        val marca = Marca(nomeMarca)

        requireActivity().contentResolver.insert(TelemoveisContentProvider.ENDERECO_MARCA, marca.toContentValues())

        if (id == null){
            binding.editTextNomeMarca.error = getString(R.string.imposivel_guardar_marca)
            return
        }

        Toast.makeText(requireContext(), getString(R.string.marca_guardada), Toast.LENGTH_LONG).show()
        voltarlistaMarcas()
    }

    private fun voltarlistaMarcas() {
        findNavController().navigate(R.id.action_EditarMarcaFragment_to_ListaMarcasFragment)
    }

}