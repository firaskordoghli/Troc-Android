package kordoghli.firas.troc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class TroquerEtape2Fragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_troquer_etape2, container, false)

    companion object {
        fun newInstance(): TroquerEtape2Fragment = TroquerEtape2Fragment()
    }
}