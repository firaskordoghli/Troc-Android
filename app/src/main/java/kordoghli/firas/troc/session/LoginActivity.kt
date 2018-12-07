package kordoghli.firas.troc.session

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject
import android.content.Intent
import kordoghli.firas.troc.*


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnConnexion.setOnClickListener {
            login()
        }
    }

    private fun login(){
        if (TextUtils.isEmpty(identifiantLogin.text)) {
            identifiantLogin.error = "champ obligatoire";
            identifiantLogin.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(motDePassLogin.text)) {
            motDePassLogin.error = "champ obligatoire";
            motDePassLogin.requestFocus();
            return;
        }

        //creating volley string request
        //if everything is fine
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_LOGIN,
            Response.Listener<String> { response ->
                try {
                    //converting response to json object
                    val obj = JSONObject(response)
                    //if no error in response
                    if (obj.getBoolean("status")) {
                        Toast.makeText(applicationContext, obj.getString("msg"), Toast.LENGTH_SHORT).show()
                        //getting the user from the response
                        val userJson = obj.getJSONObject("data")
                        //starting the profile activity
                        finish()
                        startActivity(Intent(applicationContext, HomeActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, obj.getString("msg"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
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