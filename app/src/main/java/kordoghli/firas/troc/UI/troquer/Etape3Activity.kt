package kordoghli.firas.troc.UI.troquer

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses
import kotlinx.android.synthetic.main.activity_etape_3.*
import java.io.File
import java.io.IOException


class Etape3Activity : AppCompatActivity() {
    private val REQUEST_PICK_PHOTO = 1000
    lateinit var uriImage: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etape_3)

        val service = intent.getSerializableExtra(Etape2Activity.EXTRA_SERVICE) as ResponseClasses.Service
        println("********************************$service")


        button7.setOnClickListener {
            //val galleryIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            //startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO)
            val galleryIntent = Intent()
            galleryIntent.type = "image/*"
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(galleryIntent, "Select picture"), REQUEST_PICK_PHOTO)
        }
        button5.setOnClickListener {
            val intent = Intent(this, Etape4Activity::class.java)
            intent.putExtra(EXTRA_SERVICE, service)
            startActivity(intent)
        }
    }

    fun uploadImage() {
        var file: File? = null
        var imageName = file!!.name
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_PICK_PHOTO && resultCode == Activity.RESULT_OK) {
            uriImage = data!!.data
            try {
                imageView17.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, uriImage))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    companion object {
        const val EXTRA_SERVICE = "service"
    }

}