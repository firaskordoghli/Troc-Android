package kordoghli.firas.troc.UI

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.StringRequest
import com.mancj.materialsearchbar.MaterialSearchBar
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        allService()
        allServiceRecycle()
        allCategorie()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }

    private fun allService() {
        //creating volley string request
        val data = ArrayList<HashMap<String, String>>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_GET_SERVICE,
            Response.Listener<String> { response ->
                try {
                    //get response
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
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
                    val adapter = SimpleAdapter(activity, data, R.layout.list_home_item_prototype, from, to)
                    listViewHome.adapter = adapter

                    searchBar.addTextChangeListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        }
                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            //SEARCH FILTER
                            adapter.getFilter().filter(charSequence)
                            recyvlerviewHome.visibility = View.GONE
                        }
                        override fun afterTextChanged(editable: Editable) {

                        }
                    })


                    listViewHome.setOnItemClickListener { parent, view, position, id ->
                        val objItem = jasonArray.getJSONObject(id.toInt())
                        val intent = Intent(activity, DetailsServiceActivity::class.java)
                        intent.putExtra("id", objItem.getInt("id"))
                        startActivity(intent)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            ErrorListener { error ->
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("username", identifiantLogin.text.toString())
                params.put("password", motDePassLogin.text.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun allCategorie() {
        //creating volley string request
        val data = ArrayList<String>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_CATEGORIE,
            Response.Listener<String> { response ->
                try {
                    //get response
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
                        val obj = jasonArray.getJSONObject(i)
                        data.add(obj.getString("Categorie"))
                        println(data)
                    }
                    recycleCategorie.layoutManager = LinearLayoutManager(context,OrientationHelper.HORIZONTAL,false)
                    recycleCategorie.adapter = CategorieHomeAdapter(data)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            ErrorListener { error ->
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }) {
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun allServiceRecycle() {
        //creating volley string request
        val data = ArrayList<ResponseClasses.Service>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_GET_SERVICE,
            Response.Listener<String> { response ->
                try {
                    //get response
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
                        val obj = jasonArray.getJSONObject(i)
                        val service = ResponseClasses.Service(
                            obj.getInt("id"),
                            obj.getString("titre"),
                            obj.getString("description"),
                            obj.getString("categorie"),
                            obj.getString("type"),
                            obj.getString("idUser")
                        )
                        data.add(service)
                        recyvlerviewHome.layoutManager = LinearLayoutManager(context)
                        recyvlerviewHome.adapter = ServiceHomeAdapter(data)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            ErrorListener { error ->
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }) {
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