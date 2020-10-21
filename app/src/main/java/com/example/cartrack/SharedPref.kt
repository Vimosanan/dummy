package com.example.cartrack

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context?) {
    private var mySharedPref: SharedPreferences = context!!.getSharedPreferences("filename",Context.MODE_PRIVATE)
    fun setNightModeState(state:Boolean){
        val editor = mySharedPref.edit();
        editor.putBoolean("Night Mode",state)
        editor.commit()
    }
    fun loadNightModeState():Boolean{
        return mySharedPref.getBoolean("Night Mode",false)
    }

}
