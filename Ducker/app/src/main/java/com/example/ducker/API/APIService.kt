package com.example.ducker.API

import com.example.ducker.data.Quack
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Url

interface APIService {

    @GET("quacks")
    fun getPosts(@Header("auth") auth: String): Call<List<Quack>>
}