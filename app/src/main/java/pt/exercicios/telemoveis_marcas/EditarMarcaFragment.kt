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
import pt.exercicios.telemoveis_marcas.databinding.FragmentEditarMarcaBinding


class EditarMarcaFragment : Fragment() {

    private var marcas: Marca?= null
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

        val marca = EditarMarcaFragmentArgs.fromBundle(requireArguments()).marcas

        if (marca != null){
            activity.atualizaModelo(R.string.editar_marca)

            binding.editTextNomeMarcaPrincipal.setText(marca.nome_marca)
        }else{
            activity.atualizaModelo(R.string.nova_marca_label)
        }

        this.marcas = marca
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun voltarlistaMarcas() {
        findNavController().navigate(R.id.action_EditarMarcaFragment_to_ListaMarcasFragment)
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
        val nomeMarca1 = binding.editTextNomeMarcaPrincipal.toString()
        if (nomeMarca1.isBlank()){
            binding.editTextNomeMarcaPrincipal.error = getString(R.string.nomeMarca_obrigatorio)
            binding.editTextNomeMarcaPrincipal.requestFocus()
            return
        }

        if (marcas== null){
            val marca = Marca(nomeMarca1)

            insereMarca(marca)
        }else{
            val marca = marcas!!
            marca.nome_marca = nomeMarca1

            alteraMarca(marca)
        }
    }

    private fun alteraMarca(marcas: Marca) {
        val enderecoMarca = Uri.withAppendedPath(TelemoveisContentProvider.ENDERECO_MARCA, marcas.idMarca.toString())
        val MarcasAlteradas = requireActivity().contentResolver.update(enderecoMarca, marcas.toContentValues(), null, null)

        if(MarcasAlteradas == 1){
            Toast.makeText(requireContext(), getString(R.string.marca_alterada_com_sucesso), Toast.LENGTH_LONG).show()
            voltarlistaMarcas()
        }else{
            binding.editTextNomeMarcaPrincipal.error = getString(R.string.imposivel_guardar_marca)
        }
    }

    private fun insereMarca(marca: Marca) {
        val id = requireActivity().contentResolver.insert(
            TelemoveisContentProvider.ENDERECO_MARCA,
            marca.toContentValues()
        )

        if (id == null) {
            binding.editTextNomeMarcaPrincipal.error =
                getString(R.string.imposivel_guardar_marca)
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.marca_guardada),
            Toast.LENGTH_LONG
        ).show()
        voltarlistaMarcas()
    }
}