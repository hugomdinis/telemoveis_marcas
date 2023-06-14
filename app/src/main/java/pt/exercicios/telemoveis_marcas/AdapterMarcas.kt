package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AdapterMarcas(val fragment: ListaMarcasFragment): RecyclerView.Adapter<AdapterMarcas.ViewHolderMarcas>() {
    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderMarcas(contentor: View): RecyclerView.ViewHolder(contentor){
        private val textViewMarca = contentor.findViewById<TextView>(R.id.textViewMarcaPrincipal)

        init {
            contentor.setOnClickListener {
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

            internal var marcas: Marca? = null
            set(value){
                field = value
                textViewMarca.text = marcas?.nome_marca ?: ""
            }

            fun seleciona(){
                viewHolderSeleccionado = this
                fragment.marcaSelecionada = marcas
                itemView.setBackgroundResource(R.color.item_selecionado)
            }

            fun desSeleciona(){
                itemView.setBackgroundResource(android.R.color.white)
            }
        }

        private var viewHolderSeleccionado : AdapterMarcas.ViewHolderMarcas? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMarcas {
        return ViewHolderMarcas(
            fragment.layoutInflater.inflate(R.layout.item_marca, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolderMarcas, position: Int) {
        cursor!!.moveToPosition(position)
        holder.marcas=Marca.fromCursor(cursor!!)
    }
}
