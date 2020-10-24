package com.example.cartrack

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cartrack.api.NetworkClient
import com.example.cartrack.api.ApiInterface
import com.example.cartrack.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailsFragment : Fragment() {
    private var key : Int? = null
    private var user_Name: TextView? =null
    private var user_username: TextView? =null
    private var user_email: TextView? =null
    private var user_phone: TextView? =null
    private var user_website: TextView? =null
    private var user_street: TextView? =null
    private var user_Suite: TextView? =null
    private var user_City: TextView? =null
    private var user_Zipcode: TextView? =null
    private var apiInterface: ApiInterface? = null
    @SuppressLint("CutPasteId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_details, container, false)
        user_Name = view.findViewById<View>(R.id.user_Name) as TextView?
        user_username = view.findViewById<View>(R.id.user_username) as TextView?
        user_email = view.findViewById<View>(R.id.user_email) as TextView?
        user_phone = view.findViewById<View>(R.id.user_phone) as TextView?
        user_website = view.findViewById<View>(R.id.user_website) as TextView?
        user_street = view.findViewById<View>(R.id.user_street) as TextView?
        user_Suite = view.findViewById<View>(R.id.user_Suite) as TextView?
        user_City = view.findViewById<View>(R.id.user_City) as TextView?
        user_Zipcode = view.findViewById<View>(R.id.user_Zipcode) as TextView?

        key = this.arguments?.getInt("Key")
        apiInterface = NetworkClient.buildService(ApiInterface::class.java)
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
        val call = apiInterface!!.singleUser(key!!)
        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful){
                    user_Name?.text = response.body()?.name.toString()
                    user_username?.text = response.body()?.username.toString()
                    user_email?.text = response.body()?.email.toString()
                    user_phone?.text = response.body()?.phone.toString()
                    user_website?.text = response.body()?.website.toString()
                    user_street?.text = response.body()?.address?.street.toString()
                    user_Suite?.text = response.body()?.address?.suite.toString()
                    user_City?.text = response.body()?.address?.city.toString()
                    user_Zipcode?.text = response.body()?.address?.zipcode.toString()
                    //Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                TODO("Not yet implemented")
                Toast.makeText(activity, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}