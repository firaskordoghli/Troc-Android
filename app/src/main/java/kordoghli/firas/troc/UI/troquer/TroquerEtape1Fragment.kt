package kordoghli.firas.troc.UI.troquer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kotlinx.android.synthetic.main.fragment_troquer_etape1.view.*


class TroquerEtape1Fragment:  Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_troquer_etape1, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "etape 2"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        view.toEtape2.setOnClickListener { view ->
            var troquerEtape2Fragment = TroquerEtape2Fragment()
            fragmentManager!!.beginTransaction().replace(R.id.container,troquerEtape2Fragment).commit()
        }
        return view
    }


    companion object {
        fun newInstance(): TroquerEtape1Fragment =
            TroquerEtape1Fragment()

    }
}