package kordoghli.firas.troc.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kordoghli.firas.troc.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)

    }
}
