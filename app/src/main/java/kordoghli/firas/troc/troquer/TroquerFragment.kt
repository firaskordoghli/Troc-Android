package kordoghli.firas.troc.troquer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kotlinx.android.synthetic.main.fragment_troquer.view.*

class TroquerFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_troquer, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "Que Troquez vous ?"
        //(activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        view.btnToEtape2.setOnClickListener { view ->
            var troquerEtape1Fragment = TroquerEtape1Fragment()
            fragmentManager!!.beginTransaction().replace(R.id.container,troquerEtape1Fragment).commit()
        }
        return view
    }

    companion object {
        fun newInstance(): TroquerFragment {
            return TroquerFragment()
        }
    }

}