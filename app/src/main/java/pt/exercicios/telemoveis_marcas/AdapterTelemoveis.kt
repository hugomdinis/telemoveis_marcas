package pt.exercicios.telemoveis_marcas

import android.database.Cursor
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import android.view.ViewGroup

class AdapterTelemoveis: RecyclerView.Adapter<AdapterTelemoveis.ViewHolderMarca>() {
    var cursor:Cursor? = null
        set(value) {
            field = value
            notifyDataSetChanged()
    }

    inner class ViewHolderMarca(intemView: View): ViewHolder(intemView){

    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolderMarca {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return cursor?.count?:0
    }

    override fun onBindViewHolder(p0: ViewHolderMarca, p1: Int) {
        TODO("Not yet implemented")
    }
}