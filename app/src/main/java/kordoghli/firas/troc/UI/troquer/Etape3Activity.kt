package kordoghli.firas.troc.UI.troquer

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.VolleySingleton
import kotlinx.android.synthetic.main.activity_etape_3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*


class Etape3Activity : AppCompatActivity() {
    private val REQUEST_PICK_PHOTO = 1000
    lateinit var bitmap: Bitmap
    private val url = "http://192.168.1.3:3000/UploadImage/service"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etape_3)

        val service = intent.getSerializableExtra(Etape2Activity.EXTRA_SERVICE) as ResponseClasses.Service

        button7.setOnClickListener {
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "Select picture"), REQUEST_PICK_PHOTO)
        }

        button5.setOnClickListener {
            uploadImage()
            val intent = Intent(this, Etape4Activity::class.java)
            intent.putExtra(EXTRA_SERVICE, service)
            startActivity(intent)
        }

        button10.setOnClickListener {
            val intent = Intent(this, Etape2Activity::class.java)
            intent.putExtra(EXTRA_SERVICE, service)
            startActivity(intent)
        }
    }

    fun uploadImage() {
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                try {
                    val obj = JSONObject(response)
                    Toast.makeText(this, "response", Toast.LENGTH_LONG).show()
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { volleyError ->
                Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show()
            }) {
            @RequiresApi(Build.VERSION_CODES.O)
            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["filename"] = imageToString(bitmap)
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun imageToString(bitmap: Bitmap): String {
        val byteOutOfString = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOutOfString)
        val imagBytes = byteOutOfString.toByteArray()
        return Base64.getEncoder().encodeToString(imagBytes)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK && data != null) {
            val path = data.data
            try {
                bitmap = MediaStore.Images.Media.getBitmap(contentResolver, path)
                imageView17.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val EXTRA_SERVICE = "service"
    }

}