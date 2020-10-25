package com.example.cartrack.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.cartrack.R
import com.example.cartrack.singleUser.MapViewActivity
import com.example.cartrack.singleUser.UserComapnyFragment
import com.example.cartrack.singleUser.UserDetailsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class SingleUserActivity : BaseActivity() {
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private var iD : Int = 0
    private val args= Bundle()
    private var lat:String? = null
    private var lng:String? = null
    private var addressName:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setDarkAction()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_user)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "User Profile"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)
        /*** Get data from Adapter  */
        val intent = intent
        iD = intent.getIntExtra("ID", 0)
        lat = intent.getStringExtra("lat")
        lng = intent.getStringExtra("lng")
        addressName = intent.getStringExtra("addressName")

        var navigationView = findViewById<View>(R.id.BottomNavigation) as BottomNavigationView
        navigationView.bringToFront()

        val fragment = UserDetailsFragment()
        args.putInt("Key", iD)
        fragment.arguments = args
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.add(R.id.container_fragment, fragment)
        fragmentTransaction!!.commit()


        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Personal -> {
                    val fragment1 =
                        UserDetailsFragment()
                    fragment1.arguments = args
                    actionbar.title = "User Profile"
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, fragment1)
                    fragmentTransaction!!.commit()
                }
                R.id.Loaction -> {
                    val intent = Intent(this, MapViewActivity::class.java)
                    intent.putExtra("lat", lat)
                    intent.putExtra("lng", lng)
                    intent.putExtra("addressName", addressName)
                    startActivity(intent)
                }
                R.id.company -> {
                    val fragment3 =
                        UserComapnyFragment()
                    fragment3.arguments = args
                    actionbar.title = "User Company"
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, fragment3)
                    fragmentTransaction!!.commit()
                }
            }
            true
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}