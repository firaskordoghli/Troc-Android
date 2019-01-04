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
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.ErrorListener
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.VolleySingleton
import kordoghli.firas.troc.data.adapters.CategorieHomeAdapter
import kordoghli.firas.troc.data.adapters.ServiceUserInfoAdapter
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*


class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        allService()
        allCategorie()
        allCategorieInformatique()
        allCategorieAnimaux()
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
                        hp["categorie"] = obj.getString("categorie")
                        hp["type"] = obj.getString("type")
                        hp["idUser"] = obj.getString("idUser")
                        hp["imageView4"] = Integer.toString(R.drawable.placeholder)
                        data.add(hp)
                    }
                    val from = arrayOf("titre", "description")
                    val to = intArrayOf(R.id.titre, R.id.description)
                    val adapter = SimpleAdapter(activity, data, R.layout.list_home_item_prototype, from, to)
                    listViewHome.adapter = adapter
                    setListViewHeightBasedOnChildren(listViewHome)
                    searchBar.addTextChangeListener(object : TextWatcher {
                        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                        }

                        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                            //SEARCH FILTER
                            adapter.getFilter().filter(charSequence)
                            textView42.visibility = View.GONE
                            recycleInformatique.visibility = View.GONE
                            textView44.visibility = View.GONE
                            recycleTransport.visibility = View.GONE
                            textView45.visibility = View.GONE
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
                    }
                    recycleCategorie.layoutManager = LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
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

    private fun allCategorieInformatique() {
        //creating volley string request
        val data = ArrayList<ResponseClasses.Service>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_CATEGORIE_INFORMATIQUE,
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
                        recycleInformatique.layoutManager =
                                LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
                        recycleInformatique.adapter = ServiceUserInfoAdapter(data)
                    }
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

    private fun allCategorieAnimaux() {
        //creating volley string request
        val data = ArrayList<ResponseClasses.Service>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_CATEGORIE_ANIMAUX,
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
                        recycleTransport.layoutManager =
                                LinearLayoutManager(context, OrientationHelper.HORIZONTAL, false)
                        recycleTransport.adapter = ServiceUserInfoAdapter(data)

                    }
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

    fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth = MeasureSpec.makeMeasureSpec(listView.width, MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0)
                view!!.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            view!!.measure(desiredWidth, MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }

}