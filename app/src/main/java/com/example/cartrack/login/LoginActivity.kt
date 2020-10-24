package com.example.cartrack.login

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.MainActivity
import com.example.cartrack.R
import com.example.cartrack.register.RegisterActivity
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.ActivityLoginBinding
import com.example.cartrack.entity.AppUser
import com.example.cartrack.util.Result
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.viewmodel = loginViewModel
        binding.emailObservable = loginViewModel.emailObservable
        binding.passwordObservable = loginViewModel.passwordObservable
        //}
        errorObserveViewModel(binding)
        observeViewModel()

    }
    private fun observeViewModel() {
        loginViewModel.result.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> navigateToHome()

                    is Result.Loading -> {

                    }
                    is Result.Error -> {
                        Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    fun errorObserveViewModel(binding: ActivityLoginBinding){
        loginViewModel.error.observe(this, Observer {
            it.let {
                when (it) {
                    "email" -> {
                        binding.resetEmailMain.error = "Please Enter the Email"
                        binding.resetEmailMain.requestFocus()
                    }
                    "password" -> {
                        binding.LoginPasswordMain.error = "Please Enter the Password"
                        binding.LoginPasswordMain.requestFocus()
                    }
                    "NavigateToRegister" -> {
                        navigateToRegister()
                    }
                    "LogedIn"->{
                        navigateToHome()
                    }
                    else -> {

                    }
                }
            }
        })
    }
    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }
}