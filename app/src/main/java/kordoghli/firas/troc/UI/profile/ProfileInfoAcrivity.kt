package kordoghli.firas.troc.UI.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kordoghli.firas.troc.R

class ProfileInfoAcrivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)
        supportActionBar?.title = "Parametre"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        openFragment(ProfileInfoFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.containerProfileInfo, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}