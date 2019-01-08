package kordoghli.firas.troc.UI.troquer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.Communicator
import kordoghli.firas.troc.data.ResponseClasses


class TroquerActivity : AppCompatActivity(),Communicator  {
    override fun etape1data(data: ResponseClasses.Service) {
        TroquerFragment.newInstance().saveDate(data)
    }

    override fun etape2data(data: ResponseClasses.Service) {
        TroquerEtape1Fragment.newInstance().saveDate(data)
    }

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