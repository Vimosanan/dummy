package com.example.cartrack.api

import com.example.cartrack.response.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiInterface {

    @GET("users")
    suspend fun getPostJson(): List<User>


    @GET("users/{id}")
    suspend fun singleUser(@Path("id") id: Int): User
}