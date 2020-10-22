package com.example.cartrack

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class SingleUserActivity : BaseActivity() {
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private var iD : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setDarkAction()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_user)
        /*** Get data from Adapter  */
        val intent = intent
        iD = intent.getStringExtra("ID").toString()
        var navigationView = findViewById<View>(R.id.BottomNavigation) as BottomNavigationView
        navigationView.bringToFront()
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.replace(R.id.container_fragment, UserDetailsFragment())
        fragmentTransaction!!.commit()

        navigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Personal -> {
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, UserDetailsFragment())
                    fragmentTransaction!!.commit()
                }
                R.id.Loaction -> {
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, MapViewFragment())
                    fragmentTransaction!!.commit()
                }
                R.id.company -> {
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, UserComapnyFragment())
                    fragmentTransaction!!.commit()
                }
            }
            true
        }
    }
}