package pt.exercicios.telemoveis_marcas

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import pt.exercicios.telemoveis_marcas.databinding.FragmentNovaMarcaBinding
import pt.exercicios.telemoveis_marcas.databinding.FragmentNovoTelemovelBinding


class NovaMarcaFragment : Fragment() {

    private var _binding: FragmentNovaMarcaBinding? = null

    private val binding get () = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        _binding = FragmentNovaMarcaBinding.inflate(inflater, container, false)
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
                voltarlistaMarcass()
                true
            }
            else -> false
        }
    }

    private fun guardarMarca() {
        TODO("Not yet implemented")
    }

    private fun voltarlistaMarcass() {
        findNavController().navigate(R.id.action_novaMarcaFragment_to_ListaMarcasFragment)
    }

}