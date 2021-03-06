package kordoghli.firas.troc.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses

class CategorieHomeAdapter(val items: ArrayList<ResponseClasses.Categorie>, val clickListener: (ResponseClasses.Categorie) -> Unit) : RecyclerView.Adapter<CategorieHomeAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titre: TextView = itemView.findViewById(R.id.textView40)
        fun bind(categorie: ResponseClasses.Categorie, clickListener: (ResponseClasses.Categorie) -> Unit) {
            titre.text = categorie.categorie
            itemView.setOnClickListener { clickListener(categorie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycleview_categorie_prototype, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.titre.text = items[p1].categorie
        (p0 as ViewHolder).bind(items[p1],clickListener)
    }


}