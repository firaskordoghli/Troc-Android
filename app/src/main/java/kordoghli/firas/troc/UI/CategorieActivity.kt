package kordoghli.firas.troc.UI

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.VolleySingleton
import kordoghli.firas.troc.data.adapters.ListCategorieAdapter
import kotlinx.android.synthetic.main.activity_categorie.*
import org.json.JSONArray
import org.json.JSONException


class CategorieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categorie)
        val categorie = intent.getStringExtra("categorie")
        supportActionBar?.title = "categorie $categorie"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        getServiceByCategorie(categorie)
    }

    private fun getServiceByCategorie(categorie: String) {
        val services = ArrayList<ResponseClasses.Service>()
        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_GET_SERVICE_BY_CATEGORIE, Response.Listener<String> { response ->
                try {
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
                        services.add(service)
                        listViewCategorie.adapter=ListCategorieAdapter(applicationContext,services)
                        setListViewHeightBasedOnChildren(listViewCategorie)
                        listViewCategorie.setOnItemClickListener { parent, view, position, id ->
                            val objItem = jasonArray.getJSONObject(id.toInt())
                            val intent = Intent(this, DetailsServiceActivity::class.java)
                            intent.putExtra("id", objItem.getInt("id"))
                            startActivity(intent)
                        }
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("categorie", categorie)
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0)
                view!!.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            view!!.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }

}