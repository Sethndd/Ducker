package com.example.ducker.API

import com.example.ducker.data.Quack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface  QuackAPI {
    @GET("quacks")
    fun getPosts(@Header("auth") auth: String): Call<List<Quack>>
}