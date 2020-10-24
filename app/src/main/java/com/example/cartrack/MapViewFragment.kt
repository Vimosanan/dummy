package com.example.cartrack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cartrack.api.ApiInterface
import com.example.cartrack.response.User
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MapViewFragment : Fragment(), OnMapReadyCallback {

    private var apiInterface: ApiInterface? = null
    private var key : Int? = null
    var mMap: GoogleMap? = null

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



//        // Get the SupportMapFragment and request notification when the map is ready to be used.
//        val mapFragment = activity?.supportFragmentManager?.findFragmentById(R.id.map) as? SupportMapFragment
//        mapFragment?.getMapAsync(this)

        key = this.arguments?.getInt("Key")
        return view
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val fragment =
//           activity?.supportFragmentManager?.findFragmentById(R.id.googleMap)
//        fragment!!.getMapAsync(this)
//    }


    //Added public method to be called from the Activity
    fun placeMarker(
        title: String?,
        lat: Double,
        lon: Double
    ) {
        if (mMap != null) {
            val marker = LatLng(lat, lon)
            mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(marker, 15f))
            mMap?.addMarker(MarkerOptions().title(title).position(marker))
        }
    }
    private fun getSingleUserData(){
        val call = apiInterface!!.singleUser(key!!)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    val lat = response.body()?.address?.geo?.lat
                    val lng = response.body()?.address?.geo?.lng
                    val myPlace = LatLng(response.body()?.address?.geo?.lat!!.toDouble(), response.body()?.address?.geo?.lng!!.toDouble())  // this is New York
                   // map.addMarker(MarkerOptions().position(myPlace).title("My Favorite City"))
                   // map.moveCamera(CameraUpdateFactory.newLatLng(myPlace))
                    //map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPlace, 12.0f))
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

            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
                Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        googleMap?.apply {
            val sydney = LatLng(-33.852, 151.211)
            addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )
        }
    }


}