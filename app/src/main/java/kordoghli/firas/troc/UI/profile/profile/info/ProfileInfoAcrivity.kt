package kordoghli.firas.troc.UI.profile.profile.info

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.WindowManager
import kordoghli.firas.troc.R

class ProfileInfoAcrivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        supportActionBar?.apply {
            title = "Parametre"
            setDisplayHomeAsUpEnabled(true)
        }
        openFragment(ProfileInfoFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.apply {
            replace(R.id.containerProfileInfo, fragment)
            addToBackStack(null)
            commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

//    override fun onOptionsItemSelected(item: MenuItem)= when(item.itemId){
//           R.id.home -> {
//               NavUtils.navigateUpFromSameTask()
//           } else -> super.onOptionsItemSelected(item)
//        }
//    }
}