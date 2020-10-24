package com.example.cartrack

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * An activity that displays a Google map with a marker (pin) to indicate a particular location.
 */
// [START maps_marker_on_map_ready]
class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    // [START_EXCLUDE]
    // [START maps_marker_get_map_async]
    private var lat:String? = null
    private var lng:String? = null
    private var addressName:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_view)
        if (getString(R.string.maps_api_key).isEmpty()) {
            Toast.makeText(this, "Add your own API key in MapWithMarker/app/secure.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }
        val intent = intent
        lat = intent.getStringExtra("lat")
        lng = intent.getStringExtra("lng")
        addressName = intent.getStringExtra("addressName")
        // Get the SupportMapFragment and request notification when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }
    // [END maps_marker_get_map_async]
    // [END_EXCLUDE]

    // [START maps_marker_on_map_ready_add_marker]
    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.apply {
            val sydney = LatLng(lat!!.toDouble(), lng!!.toDouble())
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title(addressName)
            )
            // [START_EXCLUDE silent]
            moveCamera(CameraUpdateFactory.newLatLng(sydney))
            // [END_EXCLUDE]
        }
    }
    // [END maps_marker_on_map_ready_add_marker]
}
