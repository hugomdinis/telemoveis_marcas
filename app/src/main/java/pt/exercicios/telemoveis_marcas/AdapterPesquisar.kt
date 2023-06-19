package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AdapterPesquisar(val fragment: PesquisaFragment) : RecyclerView.Adapter<AdapterPesquisar.ViewHolderTelemoveis> () {

    var cursor: Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolderTelemoveis(contentor: View): RecyclerView.ViewHolder(contentor){
        private val textViewModelo = contentor.findViewById<TextView>(R.id.textViewModelo)
        private val textViewMarca = contentor.findViewById<TextView>(R.id.textViewMarca)
        private val textViewInformacao = contentor.findViewById<TextView>(R.id.textViewInformacao)
        private val textViewAno = contentor.findViewById<TextView>(R.id.textViewAno)

        init {
            contentor.setOnClickListener {
                viewHolderSeleccionado?.desSeleciona()
                seleciona()
            }
        }

        internal var telemovel: Telemovel? = null
            set(value) {
                field = value
                textViewModelo.text = telemovel?.modelo ?: ""
                textViewMarca.text = telemovel?.marca?.nome_marca ?: ""
                textViewInformacao.text = telemovel?.informacao ?: ""
                textViewAno.text = telemovel?.ano ?: ""
            }

        fun seleciona() {
            viewHolderSeleccionado = this
            fragment.telemovelSelecionado = telemovel
            itemView.setBackgroundResource(R.color.item_selecionado)
        }

        fun desSeleciona() {
            itemView.setBackgroundResource(android.R.color.white)
        }
    }


    private var viewHolderSeleccionado : ViewHolderTelemoveis? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTelemoveis {
        return ViewHolderTelemoveis(
            fragment.layoutInflater.inflate(R.layout.item_telemovel, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AdapterPesquisar.ViewHolderTelemoveis, position: Int) {
        cursor!!.moveToPosition(position)
        holder.telemovel=Telemovel.fromCursor(cursor!!)
    }

    override fun getItemCount(): Int {
        return cursor?.count ?: 0
    }


}