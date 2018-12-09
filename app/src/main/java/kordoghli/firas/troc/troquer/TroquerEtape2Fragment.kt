package kordoghli.firas.troc.troquer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R

class TroquerEtape2Fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_troquer_etape2, container, false)
        (activity as AppCompatActivity).supportActionBar?.title = "etape 3"
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        return view
    }


    companion object {
        fun newInstance(): TroquerEtape2Fragment =
            TroquerEtape2Fragment()
    }
}