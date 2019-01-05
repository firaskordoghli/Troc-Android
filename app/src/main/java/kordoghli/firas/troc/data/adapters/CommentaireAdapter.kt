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

class CommentaireAdapter(var context: Context, var commentaires: ArrayList<ResponseClasses.Commentaire>) :
    BaseAdapter() {

    private class ViewHolder(row : View?){
        var name: TextView
        var commentaire: TextView
        var date: TextView
        var image: ImageView

        init {
            this.name= row?.findViewById(R.id.textView51) as TextView
            this.commentaire= row?.findViewById(R.id.textView52) as TextView
            this.date= row?.findViewById(R.id.textView54) as TextView
            this.image= row?.findViewById(R.id.imageView16) as ImageView

        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View?
        var viewHolder: ViewHolder
        if (convertView==null){
            var layout=LayoutInflater.from(context)
            view=layout.inflate(R.layout.list_commentaire_prototype,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var commentaire: ResponseClasses.Commentaire = getItem(position) as ResponseClasses.Commentaire
        viewHolder.name.text=commentaire.name
        viewHolder.commentaire.text=commentaire.commentaire
        viewHolder.date.text=commentaire.date

        return view as View
    }

    override fun getItem(position: Int): Any {
        return commentaires.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return commentaires.count()
    }
}