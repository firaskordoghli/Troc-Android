package kordoghli.firas.troc.troquer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R
import kotlinx.android.synthetic.main.fragment_troquer.view.*

class TroquerFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //return inflater.inflate(R.layout.fragment_troquer, container, false)
        val view: View = inflater!!.inflate(R.layout.fragment_troquer, container, false)
        view.btnToEtape2.setOnClickListener { view ->
            println("*************************")
        }
        return view
    }

    companion object {
        fun newInstance(): TroquerFragment {
            return TroquerFragment()
        }
    }
}