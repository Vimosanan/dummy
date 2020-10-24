package com.example.cartrack.register

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class RegisterViewModel :ViewModel()  {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    fun register(view: View){

    }
}