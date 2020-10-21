package com.example.cartrack.api

import com.example.cartrack.response.Users
import retrofit2.Call
import retrofit2.http.GET


interface RequestInterface {

    @GET("users")
    fun getPostJson(): Call<List<Users>>
}