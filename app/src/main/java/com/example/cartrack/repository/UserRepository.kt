package com.example.cartrack.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cartrack.dao.AppUserDao
import com.example.cartrack.entity.AppUser
import com.example.cartrack.loginSharedPrefState
import com.example.cartrack.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    var userDao: AppUserDao,
    private val sharedPref: SharedPreferences
) {

    suspend fun login(email: String, passwordHash: String): MutableLiveData<Result<AppUser>> {
        val result = MutableLiveData<Result<AppUser>>()
        withContext(Dispatchers.IO) {
            val user = userDao.findByUserNameAndPassword(email, passwordHash)
            if (user != null) {
                sharedPref.loginSharedPrefState(true)
                result.postValue(Result.Success(user))
            } else {
                result.postValue(Result.Error(Exception("User not exist!")))
            }
        }
        return result
    }
}