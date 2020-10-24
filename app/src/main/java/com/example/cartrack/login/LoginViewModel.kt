package com.example.cartrack.login

import android.content.SharedPreferences
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cartrack.entity.AppUser
import com.example.cartrack.repository.UserRepository
import com.example.cartrack.util.AppDatabase
import com.example.cartrack.util.Result
import com.example.cartrack.util.TextObservable
import kotlinx.coroutines.launch
import java.security.MessageDigest
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val db: AppDatabase,
    private val sharedPref: SharedPreferences,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _result = MutableLiveData<Result<AppUser>>()
    val result: LiveData<Result<AppUser>>
        get() = _result

    fun login(view: View) {
        val email = emailObservable.text
        val password = passwordObservable.text

        if (email.isNullOrBlank() || password.isNullOrBlank()) {

        } else {
            val passwordHash: String = hashAndSavePasswordHash(password)
            viewModelScope.launch {
                val result2 = userRepository.login(email, passwordHash)

                _result.value = result2.value

            }
        }

    }

    val emailObservable = TextObservable()
    val passwordObservable = TextObservable()

    private fun hashAndSavePasswordHash(clearPassword: String): String {
        val digest = MessageDigest.getInstance("SHA-1")
        val result = digest.digest(clearPassword.toByteArray(Charsets.UTF_8))
        val sb = StringBuilder()
        for (b in result) {
            sb.append(String.format("%02X", b))
        }
        val hashedPassword = sb.toString()
        return hashedPassword
    }
}