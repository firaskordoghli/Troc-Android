package kordoghli.firas.troc.UI.session

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import kordoghli.firas.troc.R
import kordoghli.firas.troc.UI.HomeActivity
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.SharedPrefManager
import kordoghli.firas.troc.data.User
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

        /*loginFB.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this@LoginActivity, listOf("email", "public_profile"))
        }*/
        callbackManager = CallbackManager.Factory.create()

        /*LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult) {
                    Log.v(TAG, "SUCCESS")
                    val request = GraphRequest.newMeRequest(result.accessToken) { json, graph ->
                        val user = with(json) {
                            //                        User(
//                            v = 0,
//                            id = getString("id"),
//                            email = getString("email"),
//                            username = getString("id"),
//                            fullName = getString("name"),
//                            type = FACEBOOK
//                        )
                        }
                    }
                    request.apply {
                        // TODO ADd Core-KTX
                        *//*parameters = bundleOf(
                            "fields" to "id,name,email,gender, birthday"
                        )*//*
//                        executeAsync()
                    }
                }

                override fun onCancel() {
                    Log.v(TAG, "CANCEL")
                }

                override fun onError(error: FacebookException) {
                    Log.v(TAG, "ERROR")
                }
            })*/

/*
        fbButton.registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult) {
                Log.v(TAG, "SUCCESS")
                val request = GraphRequest.newMeRequest(result.accessToken) { json, graph ->
                    val user = with(json) {
                        Log.v("User id : SUCCESS", getString("id"))
                        User(
                            1,
                            getString("id"),
                            getString("email"),
//                            getInt("phone") TODO PHONE
                            71301252
                        )
                    }

                    Log.v("user error jok bhim", "$user")
                    createAccount(user)
                }

                request.apply {
                    parameters = Bundle().apply {
                        putString("fields", "id,name,email,gender,birthday")
                    }
                    executeAsync()
                }
            }

            override fun onCancel() {
                Log.v(TAG, "CANCEL")
            }

            override fun onError(error: FacebookException) {
                Log.v(TAG, "ERROR")
            }

        })
*/
        val preference = SharedPrefManager(this)
        if (preference.isLoggedIn()) {
            finish()
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
        btnConnexion.setOnClickListener {
            login()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data);
    }

    private fun login() {
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
                        Toast.makeText(this@LoginActivity, obj.getString("msg"), Toast.LENGTH_SHORT).show()
                        //getting the user from the response
                        val userJson = obj.getJSONObject("data")
                        val user = User(
                            userJson.getInt("Id"),
                            userJson.getString("username"),
                            userJson.getString("email"),
                            userJson.getInt("phone")
                        )
                        SharedPrefManager(this@LoginActivity).setUser(user)
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this@LoginActivity, obj.getString("msg"), Toast.LENGTH_SHORT).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this@LoginActivity, error.message, Toast.LENGTH_SHORT).show()
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

    fun toCreate(v: View) {
        startActivity(Intent(this, CreateAccountActivity::class.java))
    }

    fun createAccount(user: User) {
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_CREATE_ACCOUNT,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                     Toast.makeText(applicationContext, obj.getString("msg"), Toast.LENGTH_LONG).show()
                    //TODO Save in Online DATABASE phpmyadmin
                    //Log.v("log.v ","halooooooooo 222")
                    user.id = obj.getInt("insertedId")
                    SharedPrefManager(this@LoginActivity).setUser(user)
                    startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(
                    applicationContext,
                    volleyError.message,
                    Toast.LENGTH_LONG
                ).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("username", user.username)
                params.put("email", user.email)
                params.put("password", user.username)
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    companion object {
        private val TAG = LoginActivity::class.java.simpleName
        // Useful Methods
        fun isFacebookUserLoggedIn(): Boolean {
            val accessToken = AccessToken.getCurrentAccessToken()
            return accessToken != null && !accessToken.isExpired
        }

        fun facebookLogout() {
            LoginManager.getInstance().logOut()
        }
    }
}