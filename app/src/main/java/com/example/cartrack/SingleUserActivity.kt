package com.example.cartrack

import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView


class SingleUserActivity : BaseActivity() {
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    private var iD : Int = 0
    private val args = Bundle()
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
                    val fragment1 = UserDetailsFragment()
                    fragment1.arguments = args
                    actionbar.title = "User Profile"
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, fragment1)
                    fragmentTransaction!!.commit()
                }
                R.id.Loaction -> {
                    val fragment2 = MapViewFragment()
                    fragment2.arguments = args
                    actionbar.title = "User Location"
                    fragmentManager = supportFragmentManager
                    fragmentTransaction = fragmentManager!!.beginTransaction()
                    fragmentTransaction!!.replace(R.id.container_fragment, fragment2)
                    fragmentTransaction!!.commit()
                }
                R.id.company -> {
                    val fragment3 = UserComapnyFragment()
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