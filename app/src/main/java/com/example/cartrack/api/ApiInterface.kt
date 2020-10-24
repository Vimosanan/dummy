package com.example.cartrack.api

import com.example.cartrack.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {

    @GET("users")
    fun getPostJson(): Call<List<User>>


    @GET("users/{id}")
    fun singleUser(@Path("id") id: Int): Call<User>
}