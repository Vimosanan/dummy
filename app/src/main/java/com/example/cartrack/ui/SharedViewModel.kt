package com.example.cartrack.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cartrack.loadLoginSharedPrefState
import com.example.cartrack.loginSharedPrefState
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private var sharedPreferences: SharedPreferences
):ViewModel() {

    fun logedInOrNot(): Boolean {
        val subResult = sharedPreferences.loadLoginSharedPrefState()
        return subResult
    }
    fun setLogout(){
        sharedPreferences.loginSharedPrefState(false)
    }
}