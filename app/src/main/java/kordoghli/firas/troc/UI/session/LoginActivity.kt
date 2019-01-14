package kordoghli.firas.troc.UI.session

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
import android.view.View
import android.view.WindowManager
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.Login
import com.facebook.login.LoginResult
import kordoghli.firas.troc.*
import kordoghli.firas.troc.UI.HomeActivity
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.SharedPrefManager
import kordoghli.firas.troc.data.User
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_create_account.*

class LoginActivity : AppCompatActivity() {

    var callbackManager: CallbackManager? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        callbackManager = CallbackManager.Factory.create()
        fbButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                println("***************** fb login "+result?.accessToken?.token)
                startActivity(Intent(applicationContext, HomeActivity::class.java))
            }

            override fun onCancel() {
            }

            override fun onError(error: FacebookException?) {

            }

        })
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val preference = SharedPrefManager(this)
        if (preference.isLoggedIn()){
            finish()
            startActivity(Intent(applicationContext, HomeActivity::class.java))
        }
        btnConnexion.setOnClickListener {
            login()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(resultCode,resultCode,data)
    }

    private fun login(){
        if (TextUtils.isEmpty(identifiantLogin.text)) {
            identifiantLogin.error = "champ obligatoire"
            identifiantLogin.requestFocus()
            return
        }
        if (TextUtils.isEmpty(motDePassLogin.text)) {
            motDePassLogin.error = "champ obligatoire"
            motDePassLogin.requestFocus()
            return
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
                        val user = User(
                            userJson.getInt("Id"),
                            userJson.getString("username"),
                            userJson.getString("email"),
                            userJson.getInt("phone")
                        )
                        SharedPrefManager(applicationContext).setUser(user)
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
    public fun toCreate(v:View){
        startActivity(Intent(this, CreateAccountActivity::class.java))
    }
}