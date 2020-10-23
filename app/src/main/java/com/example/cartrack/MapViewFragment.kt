package com.example.cartrack

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cartrack.api.NetworkClient
import com.example.cartrack.api.RequestInterface
import com.example.cartrack.response.Users
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapViewFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    private var key : Int? = null
    private var  mapView : com.google.android.gms.maps.MapView? = null
    private var requestInterface: RequestInterface? = null
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view: View = inflater.inflate(
            com.example.cartrack.R.layout.fragment_map_view,
            container,
            false
        )
        mapView = view.findViewById(com.example.cartrack.R.id.mapViewSub)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        mapView!!.onCreate(savedInstanceState)
        key = this.arguments?.getInt("Key")

//        map = mapView.getMap()
//        map!!.uiSettings.isMyLocationButtonEnabled = false
//        map!!.isMyLocationEnabled = true

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
//        try {
//            MapsInitializer.initialize(this.activity)
//        } catch (e: GooglePlayServicesNotAvailableException) {
//            e.printStackTrace()
//        }

        requestInterface = NetworkClient.buildService(RequestInterface::class.java)
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (isConnected) {
            getSingleUserData()
        }
        return view
    }
    private fun getSingleUserData(){
        val call = requestInterface!!.singleUser(key!!)
        call.enqueue(object : Callback<Users> {
            override fun onResponse(call: Call<Users>, response: Response<Users>) {
                if (response.isSuccessful) {
                    val lat = response.body()?.address?.geo?.lat
                    val lng = response.body()?.address?.geo?.lng
                    val myPlace = LatLng(response.body()?.address?.geo?.lat!!.toDouble(), response.body()?.address?.geo?.lng!!.toDouble())  // this is New York
                   // map.addMarker(MarkerOptions().position(myPlace).title("My Favorite City"))
                    map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))
                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12.0f))
//                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
//                        LatLng(
//                            lat!!.toDouble(),
//                            lng!!.toDouble()
//                        ), 10f
//                    )
//                    map?.animateCamera(cameraUpdate)
                    //Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                TODO("Not yet implemented")
                Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("Not yet implemented")
        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(p0: Marker?) = false
}