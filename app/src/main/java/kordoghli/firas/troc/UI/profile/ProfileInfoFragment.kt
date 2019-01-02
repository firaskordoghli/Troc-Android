package kordoghli.firas.troc.UI.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.SharedPrefManager
import kotlinx.android.synthetic.main.fragment_profile_info.*

class ProfileInfoFragment: Fragment(){
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_profile_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val preference = SharedPrefManager(context!!)
        var user = preference.getUser()
        infoUserName.setText(user.username)
        InfoAdressEmail.setText(user.email)
        infoTelephone.setText(user.phone.toString())
    }

    companion object {
        fun newInstance(): ProfileInfoFragment {
            return ProfileInfoFragment()
        }
    }
}