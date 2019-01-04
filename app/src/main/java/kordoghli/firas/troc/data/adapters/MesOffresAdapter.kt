package kordoghli.firas.troc.data.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kordoghli.firas.troc.R
import kordoghli.firas.troc.UI.DetailsServiceActivity
import kordoghli.firas.troc.data.ResponseClasses
import kotlinx.android.synthetic.main.gridview_mes_offres_prototype.view.*

class MesOffresAdapter: BaseAdapter {
    var serviceList = ArrayList<ResponseClasses.Service>()
    var context: Context? = null

    constructor(context: Context, serviceList: ArrayList<ResponseClasses.Service>) : super() {
        this.context = context
        this.serviceList = serviceList
    }

    override fun getCount(): Int {
        return serviceList.size
    }

    override fun getItem(position: Int): Any {
        return serviceList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val service = this.serviceList[position]

        var inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var gridView = inflator.inflate(R.layout.gridview_mes_offres_prototype, null)
        gridView.textView30.text = service.categorie
        gridView.textView31.text = service.titre
        gridView.imageView11.setOnClickListener {
            println("******************")
            val intent = Intent(context,DetailsServiceActivity::class.java)
            intent.putExtra("id",service.id)
            context!!.startActivity(intent)
        }

        return gridView
    }
}