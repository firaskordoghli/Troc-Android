package kordoghli.firas.troc.data.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses

class ServiceUserInfoAdapter(val posts: ArrayList<ResponseClasses.Service>, val clickListener: (ResponseClasses.Service) -> Unit) :
    RecyclerView.Adapter<ServiceUserInfoAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titre: TextView = itemView.findViewById(R.id.textView21)
        val type: TextView = itemView.findViewById(R.id.textView29)
        fun bind(service: ResponseClasses.Service,clickListener: (ResponseClasses.Service) -> Unit){
            titre.text = service.titre
            type.text = service.type
            itemView.setOnClickListener { clickListener(service) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclview_profile_prototype, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        (holder as ViewHolder).bind(posts[position],clickListener)
    }


}