package kordoghli.firas.troc.UI

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.ArrayList
import java.util.HashMap

class HomeFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)

/*
        val nom = arrayOf("ali", "med", "semi", "zied")
        val prenom = arrayOf("ali", "med", "semi", "zied")
        val listView = view.listViewHome

        val data = ArrayList<HashMap<String, String>>()
        for (i in nom.indices) {
            val hp = HashMap<String, String>()
            hp["titre"] = nom[i]
            hp["despriction"] = prenom[i]
            hp["imageView4"] = Integer.toString(R.drawable.profileimg)
            data.add(hp)
        }
        val from = arrayOf("titre", "despriction", "imageView4")
        val to = intArrayOf(
            R.id.titre,
            R.id.despriction,
            R.id.imageView4
        )
        val adapter = SimpleAdapter(view.context, data, R.layout.list_home_item_prototype, from, to)
        listView.adapter = adapter
*/
        AllService()
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    private fun AllService(){
        var ServicetList = ArrayList<ResponseClasses.Service>()
        //creating volley string request
        //if everything is fine
        val data = ArrayList<HashMap<String, String>>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_GET_SERVICE,
            Response.Listener<String> { response ->
                try {
                    //get response
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()){
                        val obj = jasonArray.getJSONObject(i)
                        val hp = HashMap<String, String>()
                        hp["id"] = obj.getInt("id").toString()
                        hp["titre"] = obj.getString("titre")
                        hp["description"] = obj.getString("description")
                        hp["type"] = obj.getString("type")
                        hp["idUser"] = obj.getString("idUser")
                        hp["imageView4"] = Integer.toString(R.drawable.profileimg)
                        data.add(hp)
                    }
                    val from = arrayOf("titre", "description")
                    val to = intArrayOf(R.id.titre, R.id.description)
                    val adapter = SimpleAdapter(activity,data, R.layout.list_home_item_prototype, from, to)
                    listViewHome.adapter = adapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            ErrorListener { error ->
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("username", identifiantLogin.text.toString())
                params.put("password", motDePassLogin.text.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}