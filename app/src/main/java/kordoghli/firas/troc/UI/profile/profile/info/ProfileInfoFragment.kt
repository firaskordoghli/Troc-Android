package kordoghli.firas.troc.UI.profile.profile.info

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.UI.HomeActivity
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.SharedPrefManager
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlinx.android.synthetic.main.fragment_profile_info.*
import org.json.JSONException
import org.json.JSONObject

class ProfileInfoFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preference = SharedPrefManager(context!!)
        var user = preference.getUser()
        infoUserName.setText(user.username)
        InfoAdressEmail.setText(user.email)
        infoTelephone.setText(user.phone.toString())

        enregistrerModificationBtn.setOnClickListener {
            updateAccount (user.id)
        }
    }

    companion object {
        fun newInstance(): ProfileInfoFragment {
            return ProfileInfoFragment()
        }
    }

    public fun updateAccount (id: Int){

        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_UPDATE_ACCOUNT,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(context, obj.getString("msg"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", id.toString())
                params.put("username", infoUserName.text.toString())
                params.put("email", InfoAdressEmail.text.toString())
                params.put("phone", infoTelephone.text.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        startActivity(Intent(context, HomeActivity::class.java))
    }
}