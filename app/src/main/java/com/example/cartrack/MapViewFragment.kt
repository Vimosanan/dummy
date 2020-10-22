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
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapViewFragment : Fragment() {
    private var key : Int? = null
    private var  mapView : com.google.android.gms.maps.MapView? = null
    private var requestInterface: RequestInterface? = null
    private var map: GoogleMap? = null
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
                    val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            lat!!.toDouble(),
                            lng!!.toDouble()
                        ), 10f
                    )
                    map?.animateCamera(cameraUpdate)
                    //Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Users>, t: Throwable) {
                TODO("Not yet implemented")
                Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}