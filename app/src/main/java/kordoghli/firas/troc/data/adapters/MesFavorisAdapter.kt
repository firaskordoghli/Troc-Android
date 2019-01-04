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
import kotlinx.android.synthetic.main.gridview_mes_favoris_prototype.view.*

class MesFavorisAdapter: BaseAdapter {
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
        val inflator = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val gridView = inflator.inflate(R.layout.gridview_mes_favoris_prototype, null)
        gridView.textView46.text = service.categorie
        gridView.textView43.text = service.titre
        return gridView
    }
}