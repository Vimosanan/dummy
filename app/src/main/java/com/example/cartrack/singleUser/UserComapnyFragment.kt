package com.example.cartrack.singleUser

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.R
import com.example.cartrack.api.NetworkClient
import com.example.cartrack.api.ApiInterface
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.FragmentUserComapnyBinding
import com.example.cartrack.response.User
import com.example.cartrack.util.Status
import javax.inject.Inject

class UserComapnyFragment : Fragment() {
    private var key : Int? = null
    private var apiInterface: ApiInterface? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var userDetailsViewModel: UserDetailsViewModel
    private var fragmentUserComapnyBinding: FragmentUserComapnyBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_user_comapny, container, false)

        val appComponent = (activity?.applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        val binding = FragmentUserComapnyBinding.bind(view)
        fragmentUserComapnyBinding = binding
        userDetailsViewModel =
            ViewModelProvider(this, viewModelFactory).get(UserDetailsViewModel::class.java)

        binding.viewmodel = userDetailsViewModel
        binding.comapanyNameObservable = userDetailsViewModel.companyNameObservable
        binding.catchPhraseObservable = userDetailsViewModel.catchPhraseObservable
        binding.bsObservable = userDetailsViewModel.bSObservable

        key = this.arguments?.getInt("Key")

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
            userDetailsViewModel.getSingleUsers(key!!).observe(it, Observer {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            //progressBar.visibility = View.GONE
                            resource.data?.let {
                                    user -> retrieveUser(user)
                            }!!
                        }
                        Status.ERROR -> {
                            //recyclerView!!.visibility = View.VISIBLE
                            //progressBar.visibility = View.GONE
                            Toast.makeText(activity, it.message, Toast.LENGTH_LONG).show()
                        }
                        Status.LOADING -> {
                            //progressBar.visibility = View.VISIBLE
                            //recyclerView!!.visibility = View.GONE
                            Toast.makeText(activity, "Loading", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }
    }
    private fun retrieveUser(user: User) {
        fragmentUserComapnyBinding?.comapanyNameObservable?.text = user.company.name
        fragmentUserComapnyBinding?.catchPhraseObservable?.text = user.company.catchPhrase
        fragmentUserComapnyBinding?.bsObservable?.text = user.company.bs
    }
}