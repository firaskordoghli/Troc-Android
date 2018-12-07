package kordoghli.firas.troc.troquer

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kordoghli.firas.troc.R


class TroquerEtape1Fragment:  Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_troquer_etape1, container, false)


    companion object {
        fun newInstance(): TroquerEtape1Fragment =
            TroquerEtape1Fragment()

    }
}