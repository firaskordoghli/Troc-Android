package kordoghli.firas.troc.UI.troquer

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.UI.HomeActivity
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_etape_4.*
import kotlinx.android.synthetic.main.fragment_troquer.*
import org.json.JSONException
import org.json.JSONObject

class Etape4Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etape_4)
        val service = intent.getSerializableExtra(Etape2Activity.EXTRA_SERVICE) as ResponseClasses.Service
        println("********************************$service")

        textView55.text=service.titre
        textView56.text=service.description
        textView57.text=service.categorie
        textView58.text=service.type

        button6.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Felicitation")
            builder.setMessage("Votre annonce a été créé avec succès !")
            builder.setPositiveButton("aller vers l'accueil") { dialogInterface: DialogInterface, i: Int ->
                enregistrerService(service)
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
            builder.show()
        }

        button11.setOnClickListener {
            val intent = Intent(this, Etape3Activity::class.java)
            intent.putExtra(EXTRA_SERVICE, service)
            startActivity(intent)
        }
    }

    fun enregistrerService(service: ResponseClasses.Service){
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_TROQUER,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("titre", service.titre)
                params.put("description", service.description)
                params.put("categorie", service.categorie)
                params.put("type", service.type)
                params.put("idUser",service.idUser)
                params.put("longitude", service.latitude.toString())
                params.put("latitude", service.longitude.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    companion object {
        const val EXTRA_SERVICE = "service"
    }
}