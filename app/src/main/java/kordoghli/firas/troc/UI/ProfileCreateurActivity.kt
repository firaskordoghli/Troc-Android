package kordoghli.firas.troc.UI

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.widget.LinearLayout
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.ServiceUserInfoAdapter
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_profile_createur.*
import org.json.JSONArray
import org.json.JSONException
import java.util.*
import kotlin.collections.ArrayList

class ProfileCreateurActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_createur)

        val idCreateur = intent.getStringExtra("idCreateur")
        getServiceWithId(idCreateur.toInt())

        val posts: ArrayList<String> = ArrayList()
        for (i in 1..100){
            posts.add("post # $i")
        }

        recycleViewUserInfo.layoutManager = LinearLayoutManager(this,OrientationHelper.HORIZONTAL,false)
        recycleViewUserInfo.adapter = ServiceUserInfoAdapter(posts)

    }


    private fun getServiceWithId(id: Int) {
        //creating volley string request
        val stringRequest = object : StringRequest(Request.Method.POST, EndPoints.URL_GET_User_WITH_ID,
            Response.Listener<String> { response ->
                try {
                    //converting response to json object
                    val jasonArray = JSONArray(response)
                    val obj = jasonArray.getJSONObject(0)
                    textView22.text = obj.getString("username")
                    textView26.text = obj.getString("email")
                    textView28.text = obj.getString("phone")
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