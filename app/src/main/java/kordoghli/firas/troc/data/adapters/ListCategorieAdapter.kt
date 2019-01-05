package kordoghli.firas.troc.data.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses

class ListCategorieAdapter(var context: Context, var service: ArrayList<ResponseClasses.Service>) :
    BaseAdapter() {

    private class ViewHolder(row : View?){
        var image: ImageView
        var titre: TextView
        var description: TextView
        init {
            this.image= row?.findViewById(R.id.imageView4) as ImageView
            this.titre= row?.findViewById(R.id.titre) as TextView
            this.description= row?.findViewById(R.id.description) as TextView
        }
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView==null){
            var layout= LayoutInflater.from(context)
            view=layout.inflate(R.layout.list_home_item_prototype,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var service :ResponseClasses.Service = getItem(position) as ResponseClasses.Service
        viewHolder.titre.text=service.titre
        viewHolder.description.text=service.description
        //viewHolder.image.setImageDrawable(R.drawable.placeholder)

        return view as View
    }

    override fun getItem(position: Int): Any {
        return service.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return service.count()
    }
}