package com.example.cartrack

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.cartrack.api.NetworkClient
import com.example.cartrack.api.ApiInterface
import com.example.cartrack.response.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserComapnyFragment : Fragment() {
    private var key : Int? = null
    private var company_name: TextView? =null
    private var catchPhrase: TextView? =null
    private var bS_Company: TextView? =null
    private var apiInterface: ApiInterface? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_comapny, container, false)
        company_name = view.findViewById<View>(R.id.Company_name) as TextView?
        catchPhrase = view.findViewById<View>(R.id.catchPhrase) as TextView?
        bS_Company = view.findViewById<View>(R.id.BS_Company) as TextView?

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
                    company_name?.text = response.body()?.company?.name.toString()
                    catchPhrase?.text = response.body()?.company?.catchPhrase.toString()
                    bS_Company?.text = response.body()?.company?.bs.toString()
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