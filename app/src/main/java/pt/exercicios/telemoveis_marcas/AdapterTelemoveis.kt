package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class AdapterTelemoveis(val fragment: ListaDeTelemoveisFragment) : RecyclerView.Adapter<AdapterTelemoveis.ViewHolderTelemoveis>() {
    var cursor:Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
    }

    inner class ViewHolderTelemoveis(contentor: View): ViewHolder(contentor){
        private val textViewModelo = contentor.findViewById<TextView>(R.id.textViewModelo)
        private val textViewMarca = contentor.findViewById<TextView>(R.id.textViewMarca)

        internal var telemovel: Telemovel? = null
            set(value) {
                field = value
                textViewModelo.text = telemovel?.modelo ?: ""
                textViewMarca.text = telemovel?.marca.toString()
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderTelemoveis {
        return ViewHolderTelemoveis(
            fragment.layoutInflater.inflate(R.layout.item_telemovel, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return cursor?.count?:0
    }

    override fun onBindViewHolder(holder: ViewHolderTelemoveis, position: Int) {
        cursor!!.moveToPosition(position)
        holder.telemovel = Telemovel.fromCursor(cursor!!)
    }
}