package kordoghli.firas.troc.UI.session

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.*
import kordoghli.firas.troc.UI.HomeActivity
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.User
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_create_account.*
import org.json.JSONException
import org.json.JSONObject

class CreateAccountActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        btnCreateAccount.setOnClickListener {
            createAccount()
        }
    }

    public fun createAccount (){

        if (TextUtils.isEmpty(editText9.text)) {
            editText9.error = "champ obligatoire"
            editText9.requestFocus()
            return
        }
        if (TextUtils.isEmpty(editText8.text)) {
            editText8.error = "champ obligatoire"
            editText8.requestFocus()
            return
        }
        if (TextUtils.isEmpty(editText7.text)) {
            editText7.error = "champ obligatoire"
            editText7.requestFocus()
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(editText7.text).matches()) {
            editText7.error = "email non valide"
            editText7.requestFocus()
            return
        }
        if (TextUtils.isEmpty(editText6.text)) {
            editText6.error = "champ obligatoire"
            editText6.requestFocus()
            return
        }
        if (TextUtils.isEmpty(editText5.text)) {
            editText5.error = "champ obligatoire"
            editText5.requestFocus()
            return
        }
        if (TextUtils.isEmpty(editText3.text)) {
            editText3.error = "champ obligatoire"
            editText3.requestFocus()
            return
        }

        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_CREATE_ACCOUNT,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(applicationContext, obj.getString("message"), Toast.LENGTH_LONG).show()

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(applicationContext, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("username", editText9.text.toString())
                params.put("email", editText7.text.toString())
                params.put("password", editText5.text.toString())
                return params
                }
            }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        startActivity(Intent(applicationContext, HomeActivity::class.java))
    }

}