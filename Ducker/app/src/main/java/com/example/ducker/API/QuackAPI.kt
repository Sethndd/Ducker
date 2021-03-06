package com.example.ducker.API

import com.example.ducker.data.Quack
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface  QuackAPI {
    @GET("quacks")
    fun obtenerQuacks(@Header("auth") auth: String): Call<List<Quack>>

    @POST("quacks")
    fun crearQuack(@Header("auth") auth: String,@Body quack: Quack) : Call<ResponseBody>

    @GET("quacks/{id}")
    fun obtenerQuackPorId(@Header("auth") auth: String, @Path("id") id : Int) : Call<Quack>

    @DELETE("quacks/{id}")
    fun eliminarQuack(@Header("auth") auth: String, @Path("id") id: Int) : Call<ResponseBody>

    @GET("quacks/usuario/{id}")
    fun obtenerQuacksPorUsuario(@Header("auth") auth: String, @Path("id") id: Int) :Call<List<Quack>>

    @GET("quacks/padres/{id}")
    fun obtenerPadres(@Header("auth") auth: String, @Path("id") id : Int): Call<List<Quack>>

    @GET("quacks/hijos/{id}")
    fun obtenerHijos(@Header("auth") auth: String, @Path("id") id : Int): Call<List<Quack>>

    @GET("quacks/hijos/cantidad/{id}")
    fun obtenerCantidadHijos(@Header("auth") auth: String, @Path("id") id : Int): Call<APIService.Mensaje>

    @GET("quacksseguidos")
    fun obtenerQuacksSeguidos(@Header("auth") auth: String): Call<List<Quack>>

    @GET("quackshashtag/{hashtag}")
    fun obtenerQuacksPorHashtag(@Header("auth") auth: String, @Path("hashtag") hashtag : String) : Call<List<Quack>>
}