package kordoghli.firas.troc.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_profile, container, false)

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preference = SharedPrefManager(context!!)
        var user = preference.getUser()
        textViewId.text = user.id.toString()
        textViewEmail.text = user.email
        textViewUserName.text = user.username


        view.btnLogOut.setOnClickListener { view ->
            preference.logout()
        }
    }

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }
}