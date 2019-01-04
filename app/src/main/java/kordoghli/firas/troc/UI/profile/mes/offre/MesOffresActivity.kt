package kordoghli.firas.troc.UI.profile.mes.offre

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R

class MesOffresActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mes_offres)

        supportActionBar?.title = "Vos offres"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        openFragment(MesOffresFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerMesOffres,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}