package com.example.cartrack.login

import android.content.Intent
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
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)

//        sharedpre = SharedPref(this)
//        if (sharedpre.loadLoginSharedPrefState()) {
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        //} else {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        binding.viewmodel = loginViewModel
        binding.emailObservable = loginViewModel.emailObservable
        binding.passwordObservable = loginViewModel.passwordObservable
        //}

        observeViewModel()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.registerNow -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }

        }
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
    private fun navigateToHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}