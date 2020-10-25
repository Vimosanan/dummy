@file:Suppress("DEPRECATION")

package com.example.cartrack.home

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cartrack.R
import com.example.cartrack.adapter.UserAdapter
import com.example.cartrack.api.NetworkClient
import com.example.cartrack.api.ApiInterface
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.FragmentHomeBinding
import com.example.cartrack.response.User
import com.example.cartrack.util.Result
import com.example.cartrack.util.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var homeViewModel: HomeViewModel
    private var recyclerView: RecyclerView? = null
    private var apiInterface: ApiInterface? = null
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private lateinit var adapter: UserAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(
            R.layout.fragment_home, container,
            false)
        val appComponent = (activity?.applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        val binding = FragmentHomeBinding.bind(view)
        fragmentHomeBinding = binding
        homeViewModel =
            ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.viewmodel = homeViewModel


        recyclerView = view.findViewById<View>(R.id.ListOfUser) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        apiInterface = NetworkClient.buildService(ApiInterface::class.java)

        val cm =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting
        if (isConnected) {
            setupObservers()
        }
        return view
    }


    private fun setupObservers() {
        activity?.let {
            homeViewModel.getUsers().observe(it, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            recyclerView!!.visibility = View.VISIBLE
                            //progressBar.visibility = View.GONE
                            resource.data?.let {
                                    users -> retrieveList(users) }
                        }
                        Status.ERROR -> {
                            recyclerView!!.visibility = View.VISIBLE
                            //progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            //progressBar.visibility = View.VISIBLE
                            recyclerView!!.visibility = View.GONE
                        }
                    }
                }
            })
        }
    }
    private fun retrieveList(users: List<User>) {
        adapter = UserAdapter(users,context)
        recyclerView!!.adapter = adapter
    }
}



