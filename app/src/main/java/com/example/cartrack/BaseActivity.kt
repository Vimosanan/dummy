package com.example.cartrack

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

open class

BaseActivity : AppCompatActivity(){
    private lateinit var sharedPre:SharedPref
    override fun onCreate(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onCreate(savedInstanceState, persistentState)
    }
    fun setDark() {
        sharedPre = SharedPref(this)
        if (sharedPre.loadNightModeState()){
            setTheme(R.style.AppThemeDark)
        }
        else{
            setTheme(R.style.AppThemeLite)
        }
    }
    fun setDarkAction() {
        sharedPre = SharedPref(this)
        if (sharedPre.loadNightModeState()){
            setTheme(R.style.AppThemeDarkActionBar)
        }
        else{
            setTheme(R.style.AppThemeLiteActionBar)
        }
    }

}