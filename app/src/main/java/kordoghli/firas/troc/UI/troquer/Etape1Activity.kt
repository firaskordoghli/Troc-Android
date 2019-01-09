package kordoghli.firas.troc.UI.troquer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kotlinx.android.synthetic.main.activity_etape_1.*
import org.json.JSONArray
import org.json.JSONException

class Etape1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etape_1)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        getCategorie()
        button2.setOnClickListener {
            etape1()
        }
    }

    fun etape1() {
        if (TextUtils.isEmpty(editText11.text)) {
            editText11.error = "champ obligatoire"
            editText11.requestFocus()
            return
        }
        if (TextUtils.isEmpty(editText12.text)) {
            editText12.error = "champ obligatoire"
            editText12.requestFocus()
            return
        }

        var type = ""
        if (radioButton2.isChecked) {
            type = "service"
        } else if (radioButton.isChecked) {
            type = "bien"
        }

        val preference = SharedPrefManager(this)
        var user = preference.getUser()

        val service = ResponseClasses.Service(
            99,
            editText11.text.toString(),
            editText12.text.toString(),
            spinner2.selectedItem.toString(),
            type,
            user.id.toString(),
            9.9F,
            9.9F
        )

        val intent = Intent(this, Etape2Activity::class.java)
        intent.putExtra(EXTRA_SERVICE, service)
        startActivity(intent)
    }

    fun getCategorie() {
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
                    spinner2.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, data)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    companion object {
        const val EXTRA_SERVICE = "service"
    }
}