package kordoghli.firas.troc.UI.troquer

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
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
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin
import kordoghli.firas.troc.R
import kordoghli.firas.troc.UI.HomeActivity
import kordoghli.firas.troc.UI.ProfileCreateurActivity
import kordoghli.firas.troc.data.*
import kotlinx.android.synthetic.main.activity_details_service.*
import kotlinx.android.synthetic.main.activity_etape_2.*
import kotlinx.android.synthetic.main.activity_troquer_update.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class TroquerUpdateActivity:AppCompatActivity(), PermissionsListener, LocationEngineListener {

    private lateinit var mapView: MapView
    private lateinit var map: MapboxMap
    private lateinit var permissionManager: PermissionsManager
    private lateinit var originLocation: Location
    private var locationEngine: LocationEngine? = null
    private var locationLayerPlugin: LocationLayerPlugin? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(applicationContext, getString(R.string.mapbox_access_token))
        setContentView(R.layout.activity_troquer_update)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        val idService = intent.getIntExtra("idService", 0)
        getServiceWithId(idService)
        getCategorie()
        mapView = findViewById(R.id.mapViewUpdate)
        mapView?.onCreate(savedInstanceState)

        mapView.getMapAsync { mapboxMap ->
            map = mapboxMap
            enableLocation()
            var type = ""
            if (radioButton3.isChecked) {
                type = "service"
            } else if (radioButton4.isChecked) {
                type = "bien"
            }
            button8.setOnClickListener {
                //val intent = Intent(this, Etape3Activity::class.java)
                //intent.putExtra(Etape2Activity.EXTRA_SERVICE, service)
                //startActivity(intent)
                val builder = AlertDialog.Builder(this)
                builder.setMessage("vous avez modifier votre annonce")
                builder.setPositiveButton("OUI") { dialogInterface: DialogInterface, i: Int ->

                    val stringRequest = object : StringRequest(
                        Request.Method.POST, EndPoints.URL_UPDATE_SERVICE, Response.Listener<String> { response ->
                            try {
                                val obj = JSONObject(response)
                                Toast.makeText(this, obj.getString("msg"), Toast.LENGTH_LONG).show()
                            } catch (e: JSONException) {
                                e.printStackTrace()
                            }
                        },
                        Response.ErrorListener { volleyError -> Toast.makeText(this, volleyError.message, Toast.LENGTH_LONG).show() }) {
                        @Throws(AuthFailureError::class)
                        override fun getParams(): Map<String, String> {
                            val params = HashMap<String, String>()
                            params.put("titre", editText13.text.toString())
                            params.put("description", editText14.text.toString())
                            params.put("categorie", spinner3.selectedItem.toString())
                            params.put("type", type)
                            params.put("longitude", originLocation.latitude.toString())
                            params.put("latitude", originLocation.longitude.toString())
                            params.put("id", idService.toString())
                            return params
                        }
                    }
                    VolleySingleton.instance?.addToRequestQueue(stringRequest)
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                builder.show()
            }
        }

    }

    private fun getServiceWithId(idService: Int) {
        //creating volley string request
        val stringRequest = object : StringRequest(
            Request.Method.POST, EndPoints.URL_GET_SERVICE_WITH_ID,
            Response.Listener<String> { response ->
                try {
                    //converting response to json object
                    val jasonArray = JSONArray(response)
                    val obj = jasonArray.getJSONObject(0)
                    val service = ResponseClasses.Service(
                        obj.getInt("id"),
                        obj.getString("titre"),
                        obj.getString("description"),
                        obj.getString("categorie"),
                        obj.getString("type"),
                        obj.getString("idUser"),
                        9.9F,
                        9.9F
                    )
                    editText13.setText(service.titre)
                    editText14.setText(service.description)

                    supportActionBar?.title = service.titre
                    supportActionBar?.setDisplayHomeAsUpEnabled(true)
                } catch (e: JSONException) {

                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params.put("id", idService.toString())
                return params
            }
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    fun getCategorie() {
        //creating volley string request
        val data = ArrayList<String>()
        val stringRequest = object : StringRequest(Request.Method.GET, EndPoints.URL_CATEGORIE,
            Response.Listener<String> { response ->
                try {
                    //get response
                    val jasonArray = JSONArray(response)
                    for (i in 0 until jasonArray.length()) {
                        val obj = jasonArray.getJSONObject(i)
                        data.add(obj.getString("Categorie"))
                    }
                    spinner3.adapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, data)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
            }) {
        }
        VolleySingleton.instance?.addToRequestQueue(stringRequest)
    }

    private fun enableLocation() {
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine()
            initilizeLocationLayer()
        } else {
            permissionManager = PermissionsManager(this)
            permissionManager.requestLocationPermissions(this)
        }
    }

    @SuppressLint("MissingPermission")
    private fun initializeLocationEngine() {
        locationEngine = LocationEngineProvider(this).obtainBestLocationEngineAvailable()
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
        locationLayerPlugin = LocationLayerPlugin(mapView, map, locationEngine)
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
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            locationEngine?.removeLocationUpdates()
            locationLayerPlugin?.onStart()
        }
        mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onStop() {
        super.onStop()
        locationEngine?.removeLocationUpdates()
        locationLayerPlugin?.onStop()
        mapView?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationEngine?.deactivate()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (outState != null) {
            mapView.onSaveInstanceState(outState)
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}