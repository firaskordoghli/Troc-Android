package kordoghli.firas.troc.UI

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kotlinx.android.synthetic.main.activity_details_service.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class DetailsServiceActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_service)
        val idService=intent.getIntExtra("id",0)
        //var serviceFav = ResponseClasses.Service(0, "", "", "", "", "")
        getServiceWithId(idService)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val  inflater = menuInflater;
        inflater.inflate(R.menu.details_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId){
            R.id.addFavoris -> {
                Toast.makeText(this,"favoris ajouter",Toast.LENGTH_LONG).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getServiceWithId(id: Int){
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_GET_SERVICE_WITH_ID,
            Response.Listener<String> { response ->
                try {
                    //converting response to json object
                        val jasonArray = JSONArray(response)
                        val obj = jasonArray.getJSONObject(0)
                        val service = ResponseClasses.Service(
                            obj.getInt("id"),
                            obj.getString("titre"),
                            obj.getString("description"),
                            obj.getString("categorie"),
                            obj.getString("type"),
                            obj.getString("idUser")
                        )
                    textView20.text = service.titre
                    textView19.text = service.description
                    textView17.text = service.categorie
                    textView18.text = service.type
                    imageView9.setOnClickListener {
                        val intent = Intent(this,ProfileCreateurActivity::class.java)
                        intent.putExtra("idCreateur",service.idUser)
                        startActivity(intent)
                    }
                } catch (e: JSONException) {

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", id.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}