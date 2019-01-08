package kordoghli.firas.troc.UI.troquer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.ResponseClasses

class Etape3Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_etape_3)

        val service = intent.getSerializableExtra(Etape2Activity.EXTRA_SERVICE) as ResponseClasses.Service
        println("********************************$service")
    }
}