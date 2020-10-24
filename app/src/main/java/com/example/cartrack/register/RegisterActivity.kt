package com.example.cartrack.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.cartrack.MainActivity
import com.example.cartrack.R
import com.example.cartrack.app.CartrackApplication
import com.example.cartrack.databinding.ActivityLoginBinding
import com.example.cartrack.databinding.ActivityRegisterBinding
import com.example.cartrack.util.AppDatabase
import com.example.cartrack.entity.AppUser
import com.example.cartrack.login.LoginActivity
import com.example.cartrack.util.Result
import java.security.MessageDigest
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(),
    AdapterView.OnItemSelectedListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var registerViewModel: RegisterViewModel

    private var country: String? = null
    private var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val appComponent = (applicationContext as CartrackApplication).appComponent
        appComponent.inject(this)
        super.onCreate(savedInstanceState)
        val binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        spinner = findViewById(R.id.Up_Post_AddressMain)

        ArrayAdapter.createFromResource(
            this,
            R.array.country_arrays,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner!!.adapter = adapter
        }

        registerViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)

        binding.viewmodel = registerViewModel
        binding.emailObservable = registerViewModel.emailObservable
        binding.passwordObservable = registerViewModel.passwordObservable
        binding.nameObservable = registerViewModel.nameObservable
        binding.phoneObservable = registerViewModel.phoneObservable
        binding.conformPasswordObservable = registerViewModel.conformPasswordObservable

        errorObserveViewModel(binding)
        spinner?.onItemSelectedListener = this

        observeViewModel()

    }
    private fun observeViewModel() {
        registerViewModel.result.observe(this, Observer {
            it?.let {
                when (it) {
                    is Result.Success -> navigateToLogin(it)

                    is Result.Loading -> {

                    }
                    is Result.Error -> {
                        Toast.makeText(this, it.exception.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        })
    }
    private fun errorObserveViewModel(binding: ActivityRegisterBinding){
        registerViewModel.error.observe(this, Observer {
            it.let {
                when (it) {
                    "email" -> {
                        binding.UpEmailMain.error = "Please Enter the Email"
                        binding.UpEmailMain.requestFocus()
                    }
                    "name" -> {
                        binding.UpUname.error = "Please Enter the Email"
                        binding.UpUname.requestFocus()
                    }
                    "phone" -> {
                        binding.UpPhoneNumberMain.error = "Please Enter the Phone Number"
                        binding.UpPhoneNumberMain.requestFocus()
                    }
                    "Conform" -> {
                        binding.UpConfomPasswordMain.error = "Please Enter the Conform Password"
                        binding.UpConfomPasswordMain.requestFocus()
                    }
                    "password" -> {
                        binding.UpPasswordMain.error = "Please Enter the Password"
                        binding.UpPasswordMain.requestFocus()
                    }
                    else -> {
                        binding.UpConfomPasswordMain.error = "Passwords are not matched"
                        binding.UpConfomPasswordMain.requestFocus()
                    }
                }
            }
        })
    }
    private fun navigateToLogin(it:Result<String>) {
        Toast.makeText(this, "Registration Success", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    override fun onItemSelected(parent: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
        // An item was selected. You can retrieve the selected item using
        country = parent?.getItemAtPosition(pos).toString()
    }
}

