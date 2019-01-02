package kordoghli.firas.troc.data

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.TextView
import kordoghli.firas.troc.R

class ServiceUserInfoAdapter(val posts: ArrayList<String>) : RecyclerView.Adapter<ServiceUserInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recyclview_profile_prototype,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = posts.size


    override fun onBindViewHolder(holder: ServiceUserInfoAdapter.ViewHolder, position: Int) {
        holder.firstName.text=posts[position]
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstName: TextView = itemView.findViewById(R.id.textView21)
    }
}