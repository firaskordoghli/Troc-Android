package kordoghli.firas.troc.UI

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import kordoghli.firas.troc.R
import kordoghli.firas.troc.UI.didacticiel.DidacticielActivity
import kordoghli.firas.troc.UI.profile.ProfileFragment
import kordoghli.firas.troc.UI.troquer.Etape1Activity
import kordoghli.firas.troc.UI.troquer.TroquerActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_acceuils -> {
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profils -> {
                val profileFragment = ProfileFragment.newInstance()
                openFragment(profileFragment)
                return@OnNavigationItemSelectedListener true
            }

            R.id.navigation_map -> {
                val mapFragment = MapFragment.newInstance()
                openFragment(mapFragment)
                return@OnNavigationItemSelectedListener true
            }
            /*
            R.id.navigation_aide -> {
                startActivity(Intent(this, DidacticielActivity::class.java))
                return@OnNavigationItemSelectedListener true
            }
            */

            R.id.navigation_troquer -> {
                startActivity(Intent(this, Etape1Activity::class.java))
                return@OnNavigationItemSelectedListener true
            }/*
            R.id.navigation_troquer -> {
                val troquerFragment = TroquerFragment.newInstance()
                openFragment(troquerFragment)
                return@OnNavigationItemSelectedListener true
            }*/
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val homeFragment = HomeFragment.newInstance()
        openFragment(homeFragment)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}