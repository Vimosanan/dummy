package com.example.cartrack

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView


class MainActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var drawerLayout: DrawerLayout? = null
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null
    private var toolbar: Toolbar? = null
    private var navigationView: NavigationView? = null
    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private var xyz: Switch? = null
    private lateinit var sharedpre:SharedPref

    override fun onCreate(savedInstanceState: Bundle?) {
        sharedpre = SharedPref(this)
        setDark()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        drawerLayout = findViewById(R.id.drawer)
        navigationView = findViewById(R.id.navigationView)
        navigationView!!.setNavigationItemSelectedListener(this)
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.Open,
            R.string.close
        )
        actionBarDrawerToggle!!.drawerArrowDrawable.color = resources.getColor(R.color.secondaryLightColor)
        drawerLayout!!.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.isDrawerIndicatorEnabled = true
        actionBarDrawerToggle!!.syncState()

        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()
        fragmentTransaction!!.replace(R.id.container_fragment, HomeFragment())
        fragmentTransaction!!.commit()

        val navMenu = navigationView?.menu
        val menuItem = navMenu?.findItem(R.id.switch1)
        xyz = menuItem?.actionView as Switch
        if (sharedpre.loadNightModeState()){
            xyz!!.isChecked =true
        }
        xyz!!.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                sharedpre.setNightModeState(true)
                restartApp()
            }
            else{
                sharedpre.setNightModeState(false)
                restartApp()
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                fragmentManager = supportFragmentManager
                fragmentTransaction = fragmentManager!!.beginTransaction()
                fragmentTransaction!!.replace(R.id.container_fragment, HomeFragment())
                fragmentTransaction!!.commit()
            }
            R.id.Register -> {
                var intent = Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.Login -> {
                var intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return false

    }
    fun restartApp(){
        var intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }
}

