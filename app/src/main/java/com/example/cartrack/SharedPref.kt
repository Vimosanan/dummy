package com.example.cartrack

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context?) {
    private var mySharedPref: SharedPreferences =
        context!!.getSharedPreferences("filename", Context.MODE_PRIVATE)
    fun setNightModeState(state: Boolean) {
        val editor = mySharedPref.edit();
        editor.putBoolean("Night Mode", state)
        editor.apply()
    }
    fun loadNightModeState(): Boolean {
        return mySharedPref.getBoolean("Night Mode", false)
    }
}

fun SharedPreferences.loadNightModeState(): Boolean {
    return getBoolean("Night Mode", false)
}
fun SharedPreferences.setNightModeState(state: Boolean) {
    edit()
    .putBoolean("Night Mode", state).apply()
}

fun SharedPreferences.loadLoginSharedPrefState(): Boolean {
    return getBoolean("LogedIn", false)
}

fun SharedPreferences.loginSharedPrefState(state: Boolean) {
    edit()
        .putBoolean("LogedIn", state)
        .apply()
}
