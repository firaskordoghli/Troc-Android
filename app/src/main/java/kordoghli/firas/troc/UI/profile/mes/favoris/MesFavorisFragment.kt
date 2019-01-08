package kordoghli.firas.troc.UI.profile.mes.favoris

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.DataBaseHandler
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.SharedPrefManager
import kordoghli.firas.troc.data.adapters.MesFavorisAdapter
import kordoghli.firas.troc.data.adapters.MesOffresAdapter
import kotlinx.android.synthetic.main.fragment_mes_favoris.*
import java.util.*

class MesFavorisFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mes_favoris, container, false)
        val preference = SharedPrefManager(context!!)
        var user = preference.getUser()
        mesFavoris(user.id.toString())
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): MesFavorisFragment {
            return MesFavorisFragment()
        }
    }

    private fun mesFavoris(idUser: String) {
        var adapter: MesOffresAdapter?
        val db = DataBaseHandler(context!!)
        var data = db.readData(idUser)
        val services: ArrayList<ResponseClasses.Service> = ArrayList()
        for (i in 0..(data.size - 1)) {
            val service = ResponseClasses.Service(
                data[i].id,
                data[i].titre,
                data[i].description,
                data[i].categorie,
                data[i].type,
                data[i].idUser,
                9.9F,
                9.9F
            )
            services.add(service)
            adapter = MesOffresAdapter(context!!, services)
            gridViewFavoris.adapter = adapter
        }
    }
}