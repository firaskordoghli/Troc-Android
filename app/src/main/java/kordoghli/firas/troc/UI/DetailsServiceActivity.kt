package kordoghli.firas.troc.UI

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.*
import kordoghli.firas.troc.data.adapters.CommentaireAdapter
import kotlinx.android.synthetic.main.activity_details_service.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class DetailsServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_service)
        val idService = intent.getIntExtra("id", 0)
        getServiceWithId(idService)
        getCommentaire(idService)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater;
        inflater.inflate(R.menu.details_menu, menu)
        return super.onCreateOptionsMenu(menu)
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
                        obj.getString("idUser"),
                        9.9F,
                        9.9F
                    )
                    textView20.text = service.titre
                    textView19.text = service.description
                    textView17.text = service.categorie
                    textView18.text = service.type

                    supportActionBar?.title = service.titre
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)

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
                    btnFavoris.setOnClickListener {
                        var db = DataBaseHandler(this)
                        db.insertData(service, user.id)
                        Toast.makeText(this, "added", Toast.LENGTH_SHORT).show()
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
                    btnAddComentaire.setOnClickListener {
                        addComentaire(user.username,service.id,user.id)
                        finish()
                        startActivity(getIntent())
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

    private fun addComentaire(name:String ,idAnnonce: Int, idUser: Int) {
        if (TextUtils.isEmpty(editText10.text)) {
            editText10.error = "champ obligatoire"
            editText10.requestFocus()
            return
        }
        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_ADD_COMMENTAIRE,Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("commentaires", editText10.text.toString())
                params.put("name",name)
                params.put("id_annonce", idAnnonce.toString())
                params.put("id_utilisateur", idUser.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun getCommentaire(idAnnonce: Int){
    //creating volley string request
        val commentaires = ArrayList<ResponseClasses.Commentaire>()
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_GET_COMMENTAIRE,Response.Listener<String> { response ->
                try {
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
                        val obj = jasonArray.getJSONObject(i)
                        val commentaire = ResponseClasses.Commentaire(
                            obj.getInt("id"),
                            obj.getString("commentaires"),
                            obj.getString("name"),
                            obj.getInt("id_annonce"),
                            obj.getInt("id_utilisateur"),
                            obj.getString("date")
                        )
                        commentaires.add(commentaire)
                        listViewComentaire.adapter = CommentaireAdapter(applicationContext,commentaires)
                        setListViewHeightBasedOnChildren(listViewComentaire)
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id_annonce", idAnnonce.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    public fun setListViewHeightBasedOnChildren(listView: ListView) {
        val listAdapter = listView.adapter ?: return
        val desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
        var totalHeight = 0
        var view: View? = null
        for (i in 0 until listAdapter.count) {
            view = listAdapter.getView(i, view, listView)
            if (i == 0)
                view!!.layoutParams = ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)
            view!!.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
            totalHeight += view.measuredHeight
        }
        val params = listView.layoutParams
        params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
        listView.layoutParams = params
    }

}