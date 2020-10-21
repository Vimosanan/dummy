@file:Suppress("DEPRECATION")

package com.example.cartrack

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.adapter.UserAdapter
import com.example.cartrack.api.NetworkClient
import com.example.cartrack.api.RequestInterface
import com.example.cartrack.response.Users
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class HomeFragment : Fragment() {

    private var recyclerView: RecyclerView? = null
    private var dateArray: ArrayList<Users>? = null
    private var requestInterface: RequestInterface? = null

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container,
            false)
        dateArray = ArrayList<Users>()
        recyclerView = view.findViewById<View>(R.id.ListOfUser) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        requestInterface = NetworkClient.buildService(RequestInterface::class.java)
        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (isConnected) {
            lodeRecyclerView()
        }
        return view
    }
    private fun lodeRecyclerView() {
        /*** Get Articles for list  */
        val call = requestInterface!!.getPostJson()
        call.enqueue(object : Callback<List<Users>>{
            override fun onResponse(call: Call<List<Users>>, response: Response<List<Users>>) {
                response.body()
                if (response.isSuccessful){
//                    for (respons in response.body()!!){
//                        respons.name
//                    }
                    val userAdapter = UserAdapter(response.body())
                    recyclerView!!.adapter = userAdapter
                    Toast.makeText(activity, "${response.body()?.size.toString()}", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Users>>, t: Throwable) {
                Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}



