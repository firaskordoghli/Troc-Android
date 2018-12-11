package kordoghli.firas.troc.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R


class TroquerActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_troquer)

        supportActionBar?.title = "Que Troquez vous ?"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openFragment(TroquerFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}