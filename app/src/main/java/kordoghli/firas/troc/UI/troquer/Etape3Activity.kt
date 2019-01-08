package kordoghli.firas.troc.UI.troquer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses
import kotlinx.android.synthetic.main.activity_etape_3.*

class Etape3Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etape_3)

        val service = intent.getSerializableExtra(Etape2Activity.EXTRA_SERVICE) as ResponseClasses.Service
        println("********************************$service")

        button5.setOnClickListener {
            val intent = Intent(this, Etape4Activity::class.java)
            intent.putExtra(EXTRA_SERVICE, service)
            startActivity(intent)
        }


    }

    companion object {
        const val EXTRA_SERVICE = "service"
    }
}