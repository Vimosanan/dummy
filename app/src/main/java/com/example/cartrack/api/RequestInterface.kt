package com.example.cartrack.api

import com.example.cartrack.response.Users
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface RequestInterface {

    @GET("users")
    fun getPostJson(): Call<List<Users>>


    @GET("users/{id}")
    fun singleUser(@Path("id") id: Int): Call<Users>
}