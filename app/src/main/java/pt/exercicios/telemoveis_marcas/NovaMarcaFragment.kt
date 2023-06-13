package pt.exercicios.telemoveis_marcas

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.LoaderManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        activity.idMenuAtual = R.menu.menu_main
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}