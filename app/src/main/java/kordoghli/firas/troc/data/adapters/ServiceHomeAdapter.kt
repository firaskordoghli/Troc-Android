package kordoghli.firas.troc.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses

class ServiceHomeAdapter(val items: ArrayList<ResponseClasses.Service>) :
    RecyclerView.Adapter<ServiceHomeAdapter.ViewHolderHome>() {

    class ViewHolderHome(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titre: TextView = itemView.findViewById(R.id.recycletex1)
        val description: TextView = itemView.findViewById(R.id.recycletext2)
        val moreimg: ImageView = itemView.findViewById(R.id.more)
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolderHome {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_home_prototype, parent, false)
        return ViewHolderHome(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolderHome, position: Int) {
        holder.titre.text = items[position].titre
        holder.description.text = items[position].description
        holder.moreimg.setOnClickListener {
            println("***********************")
        }
    }
}