package com.example.ducker.API

import com.example.ducker.data.Quack
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface  QuackAPI {
    @GET("quacks")
    fun obtenerQuacks(@Header("auth") auth: String): Call<List<Quack>>

    @POST("quacks")
    fun crearQuack(@Header("auth") auth: String,@Body quack: Quack) : Call<ResponseBody>
}