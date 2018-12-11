package kordoghli.firas.troc.UI

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import kordoghli.firas.troc.R
import kotlinx.android.synthetic.main.fragment_home.view.*
import java.util.ArrayList
import java.util.HashMap

class HomeFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)

        val nom = arrayOf("ali", "med", "semi", "zied")
        val prenom = arrayOf("ali", "med", "semi", "zied")
        val ls = view.listViewHome
        val data = ArrayList<HashMap<String, String>>()
        for (i in nom.indices) {
            val hp = HashMap<String, String>()
            hp["titre"] = nom[i]
            hp["despriction"] = prenom[i]
            hp["imageView4"] = Integer.toString(R.drawable.profileimg)
            data.add(hp)
        }
        val from = arrayOf("titre", "despriction", "imageView4")
        val to = intArrayOf(
            R.id.titre,
            R.id.despriction,
            R.id.imageView4
        )
        val sa = SimpleAdapter(view.context, data, R.layout.list_home_item_prototype, from, to)
        ls.adapter = sa

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}