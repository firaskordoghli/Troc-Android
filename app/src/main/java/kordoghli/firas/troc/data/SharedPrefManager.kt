package kordoghli.firas.troc.data

import android.content.Context
import android.content.Intent
import kordoghli.firas.troc.UI.LoginActivity

class SharedPrefManager (context: Context) {

    val SHARED_PREF_NAME = "simplifiedcodingsharedpref"
    val KEY_USER_ID = "keyuserid"
    val KEY_USERNAME = "keyusername"
    val KEY_EMAIL = "keyemail"
    val KEY_PHONE = "keyphone"

    val context = context
    val preference = context.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE)

    fun logout(){

        val editor = preference.edit()
        editor.clear()
        editor.apply()
        context.startActivity(Intent(context, LoginActivity::class.java));
    }

    fun setUser(user: User) {
        val editor = preference.edit()
        editor.putInt(KEY_USER_ID,user.id)
        editor.putString(KEY_USERNAME,user.username)
        editor.putString(KEY_EMAIL,user.email)
        editor.putInt(KEY_PHONE,user.phone)
        editor.apply()
    }

    fun getUser() : User {
        return User(
            preference.getInt(KEY_USER_ID, -1),
            preference.getString(KEY_USERNAME, ""),
            preference.getString(KEY_EMAIL, ""),
            preference.getInt(KEY_PHONE,0)
        )
    }

    fun isLoggedIn() : Boolean {
        return preference.getString(KEY_USERNAME, null) != null
    }

}