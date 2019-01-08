package kordoghli.firas.troc.UI.profile.mes.offre

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kordoghli.firas.troc.data.adapters.MesOffresAdapter
import kotlinx.android.synthetic.main.fragment_mes_offres.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class MesOffresFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_mes_offres, container, false)
        val preference = SharedPrefManager(context!!)
        var user = preference.getUser()
        mesOffres(user.id)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): MesOffresFragment {
            return MesOffresFragment()
        }
    }

    private fun mesOffres(id: Int) {
        var adapter: MesOffresAdapter?
        val data: ArrayList<ResponseClasses.Service> = ArrayList()
        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_GET_All_SERVICE_USER,Response.Listener<String> { response ->
                try {
                    //converting response to json object
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
                        val obj = jasonArray.getJSONObject(i)
                        val service = ResponseClasses.Service(
                            obj.getInt("id"),
                            obj.getString("titre"),
                            obj.getString("description"),
                            obj.getString("categorie"),
                            obj.getString("type"),
                            obj.getString("idUser"),
                            9.9F,
                            9.9F
                        )
                        data.add(service)
                        println(service)
                        adapter = MesOffresAdapter(context!!, data)
                        gridViewMesOffres.adapter = adapter
                    }

                } catch (e: JSONException) {

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["idUser"] = id.toString()
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}