package kordoghli.firas.troc.UI

import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.android.core.permissions.PermissionsListener
import com.mapbox.android.core.permissions.PermissionsManager
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import kordoghli.firas.troc.R
import kordoghli.firas.troc.data.EndPoints
import kordoghli.firas.troc.data.ResponseClasses
import kordoghli.firas.troc.data.VolleySingleton
import kordoghli.firas.troc.data.adapters.CommentaireAdapter
import kotlinx.android.synthetic.main.activity_details_service.*
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_troquer_etape1.*
import org.json.JSONArray
import org.json.JSONException
import java.util.HashMap

class MapFragment:Fragment(), PermissionsListener, LocationEngineListener {

    private lateinit var map: MapboxMap
    private lateinit var permissionManager: PermissionsManager
    private lateinit var originLocation: Location
    private var locationEngine: LocationEngine? = null
    private var locationLayerPlugin: LocationLayerPlugin? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Mapbox access token is configured here. This needs to be called either in your application
        // object or in the same activity which contains the mapview.
        Mapbox.getInstance(context!!, getString(R.string.mapbox_access_token))
        mapViewMap?.onCreate(savedInstanceState)

        val view: View = inflater.inflate(R.layout.fragment_map, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapViewMap.getMapAsync { mapboxMap ->
            map = mapboxMap
            enableLocation()
            //******************************
            val stringRequest = object : StringRequest(
                Request.Method.GET, EndPoints.URL_GET_SERVICE, Response.Listener<String> { response ->
                    try {
                        val jasonArray = JSONArray(response)
                        for (i in 0 until jasonArray.length()) {
                            val obj = jasonArray.getJSONObject(i)
                            val service = ResponseClasses.Service(
                                obj.getInt("id"),
                                obj.getString("titre"),
                                obj.getString("description"),
                                obj.getString("categorie"),
                                obj.getString("type"),
                                obj.getString("idUser"),
                                obj.getDouble("longitude").toFloat(),
                                obj.getString("latitude").toFloat()
                            )
                            map.addMarker(
                                MarkerOptions()
                                    //.position(LatLng(36.862499, 10.195556))
                                    .position(LatLng(service.longitude.toDouble(), service.latitude.toDouble()))
                                    .title(service.titre)
                            )

                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                Response.ErrorListener { volleyError ->
                    Toast.makeText(context, volleyError.message, Toast.LENGTH_LONG).show()
                }) {
            }
            VolleySingleton.instance?.addToRequestQueue(stringRequest)
            //******************************


        }

    }

    private fun enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            initializeLocationEngine()
            initilizeLocationLayer()
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(activity)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initializeLocationEngine() {
        locationEngine = LocationEngineProvider(context).obtainBestLocationEngineAvailable()
        locationEngine?.priority = LocationEnginePriority.HIGH_ACCURACY
        locationEngine?.activate()

        val lastLocation = locationEngine?.lastLocation
        if (lastLocation != null) {
            originLocation = lastLocation
            setCameraPosition(lastLocation)
        } else {
            locationEngine?.addLocationEngineListener(this)
        }
    }


    @SuppressLint("MissingPermission", "WrongConstant")
    private fun initilizeLocationLayer() {
        locationLayerPlugin = LocationLayerPlugin(mapViewMap, map, locationEngine)
        locationLayerPlugin?.setLocationLayerEnabled(true)
        locationLayerPlugin?.cameraMode = CameraMode.TRACKING
        locationLayerPlugin?.renderMode = RenderMode.NORMAL
    }

    private fun setCameraPosition(location: Location) {
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude), 13.0
            )
        )
    }

    override fun onExplanationNeeded(permissionsToExplain: MutableList<String>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionResult(granted: Boolean) {
        if (granted) {
            enableLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        permissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            originLocation = location
            setCameraPosition(location)
        }
    }

    override fun onConnected() {
        locationEngine?.removeLocationUpdates()
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        if (PermissionsManager.areLocationPermissionsGranted(context)) {
            locationEngine?.removeLocationUpdates()
            locationLayerPlugin?.onStart()
        }
        mapViewMap?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapViewMap?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapViewMap?.onPause()
    }

    override fun onStop() {
        super.onStop()
        locationEngine?.removeLocationUpdates()
        locationLayerPlugin?.onStop()
        mapViewMap?.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        locationEngine?.deactivate()
        mapViewMap.onDestroy()
    }



    override fun onLowMemory() {
        super.onLowMemory()
        mapViewMap?.onLowMemory()
    }

    companion object {
        fun newInstance(): MapFragment =
            MapFragment()
    }
}