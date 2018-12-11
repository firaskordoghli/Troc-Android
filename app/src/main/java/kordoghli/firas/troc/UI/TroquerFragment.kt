package kordoghli.firas.troc.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.fragment_troquer.*
import kotlinx.android.synthetic.main.fragment_troquer.view.*
import org.json.JSONException
import org.json.JSONObject

class TroquerFragment: Fragment() {
    //private var listener: OnFragmentInteractionListener? = null
    var languages = arrayOf("English", "French", "Spanish", "Hindi", "Russian", "Telugu", "Chinese", "German", "Portuguese", "Arabic", "Dutch", "Urdu", "Italian", "Tamil", "Persian", "Turkish", "Other")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_troquer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Que Troquez vous ?"
        spinner.adapter = ArrayAdapter(activity, R.layout.support_simple_spinner_dropdown_item,languages) as SpinnerAdapter?
        view.btnToEtape2.setOnClickListener {
            etape1()
        }
    }

    fun etape1 () {
        if (TextUtils.isEmpty(editText.text)){
            editText.error = "champ obligatoire"
            editText.requestFocus()
            return
        }
        spinner.onItemSelectedListener
        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_TROQUER,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(activity, obj.getString("msg"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError -> Toast.makeText(activity, volleyError.message, Toast.LENGTH_LONG).show() }) {
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("titre", editText.text.toString())
                params.put("description", editText2.text.toString())
                params.put("categorie", "no category")
                params.put("type", "service")
                params.put("idUser", "2")
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
        var troquerEtape1Fragment = TroquerEtape1Fragment()
        fragmentManager!!.beginTransaction().replace(R.id.container,troquerEtape1Fragment).commit()
    }
    companion object {
        fun newInstance(): TroquerFragment {
            return TroquerFragment()
        }
    }

}