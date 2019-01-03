package kordoghli.firas.troc.UI

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kotlinx.android.synthetic.main.activity_details_service.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class DetailsServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_service)
        val idService = intent.getIntExtra("id", 0)
        getServiceWithId(idService)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater;
        inflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.addFavoris -> {
                Toast.makeText(this, "favoris ajouter", Toast.LENGTH_LONG).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun getServiceWithId(id: Int) {
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
                        val intent = Intent(this, ProfileCreateurActivity::class.java)
                        intent.putExtra("idCreateur", service.idUser)
                        startActivity(intent)
                    }
                    val preference = SharedPrefManager(this)
                    var user = preference.getUser()
                    if (service.idUser.toInt() != user.id) {
                        textView14.visibility = View.VISIBLE
                        imageView9.visibility = View.VISIBLE
                        btnDelete.visibility = View.GONE
                        btnUpdate.visibility = View.GONE
                    } else if (service.idUser.toInt() == user.id) {
                        textView14.visibility = View.GONE
                        imageView9.visibility = View.GONE
                        btnDelete.visibility = View.VISIBLE
                        btnUpdate.visibility = View.VISIBLE
                    }

                    btnDelete.setOnClickListener {
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Attention  !!!")
                        builder.setMessage("vous voulez supprimer cette annonces ?")
                        builder.setPositiveButton("OUI") { dialogInterface: DialogInterface, i: Int ->
                            deleteService(service.id)
                            val intent = Intent(this, HomeActivity::class.java)
                            startActivity(intent)
                        }
                        builder.setNegativeButton("NON") { dialogInterface: DialogInterface, i: Int -> }
                        builder.show()
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

    private fun deleteService(id: Int) {
        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_DELETE_SERVICE,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    //Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", id.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }
}