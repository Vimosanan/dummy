package com.example.cartrack.repository

import androidx.lifecycle.MutableLiveData
import com.example.cartrack.api.ApiInterface
import com.example.cartrack.response.User
import com.example.cartrack.util.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class NetworkUserRepository @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getUsers() = apiInterface.getPostJson()
    suspend fun getSingleUsers(userID:Int) = apiInterface.singleUser(userID)

}