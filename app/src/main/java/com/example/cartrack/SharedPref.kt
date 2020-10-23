package com.example.cartrack

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context?) {
    private var mySharedPref: SharedPreferences = context!!.getSharedPreferences("filename",Context.MODE_PRIVATE)
    //private var loginSharedPref: SharedPreferences = context!!.getSharedPreferences("LogdIn",Context.MODE_PRIVATE)
    fun setNightModeState(state:Boolean){
        val editor = mySharedPref.edit();
        editor.putBoolean("Night Mode",state)
        editor.apply()
    }
    fun loginSharedPrefState(state:Boolean){
        val editor = mySharedPref.edit();
        editor.putBoolean("LogedIn",state)
        editor.apply()
    }
    fun loadLoginSharedPrefState():Boolean{
        return mySharedPref.getBoolean("LogedIn",false)
    }
    fun loadNightModeState():Boolean{
        return mySharedPref.getBoolean("Night Mode",false)
    }

}
