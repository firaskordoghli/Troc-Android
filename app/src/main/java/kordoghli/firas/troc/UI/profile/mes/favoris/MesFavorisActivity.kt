package kordoghli.firas.troc.UI.profile.mes.favoris

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R

class MesFavorisActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mes_favoris)

        supportActionBar?.title = "list des favoris"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        openFragment(MesFavorisFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerMesFavoris,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}