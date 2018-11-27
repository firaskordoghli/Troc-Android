package kordoghli.firas.troc

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView



class TroquerEtape1Fragment:  Fragment() {

    var mapView: MapView? = null
    var map: GoogleMap? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_troquer_etape1, container, false)


    companion object {
        fun newInstance(): TroquerEtape1Fragment = TroquerEtape1Fragment()

    }
}