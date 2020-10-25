package com.example.cartrack.register

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartrack.entity.AppUser
import com.example.cartrack.repository.UserRepository
import com.example.cartrack.util.Result
import com.example.cartrack.util.TextObservable
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

class RegisterViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {
    private var _result = MutableLiveData<Result<String>>()
    private var _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error
    val result: LiveData<Result<String>>
        get() = _result
    var country: String? = null

    fun register(view: View) {
        val name = nameObservable.text
        val email = emailObservable.text
        val phone = phoneObservable.text
        val conformation = conformPasswordObservable.text
        val password = passwordObservable.text


        when {
            name.isNullOrBlank()->{
                _error.value = "name"
            }
            email.isNullOrBlank() -> {
                _error.value = "email"
            }
            phone.isNullOrBlank()->{
                _error.value ="phone"
            }
            conformation.isNullOrBlank()->{
                _error.value = "Conform"
            }
            password.isNullOrBlank() -> {
                _error.value = "password"
            }
            else -> {
                if(password == conformation){
                    val passwordHash: String = hashAndSavePasswordHash(password)
                    viewModelScope.launch {
                        val result2 = userRepository.registerUser(name,email,phone,country.toString(),passwordHash)
                        _result.value = result2.value
                    }
                }
                else{
                    _error.value = "NotMatch"
                }
            }
        }

    }
    val emailObservable = TextObservable()
    val passwordObservable = TextObservable()
    val nameObservable = TextObservable()
    val phoneObservable =TextObservable()
    val conformPasswordObservable = TextObservable()

    private fun hashAndSavePasswordHash(clearPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(clearPassword.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        return sb.toString()
    }

    fun getCountry(country:String){
        this.country = country
    }
}