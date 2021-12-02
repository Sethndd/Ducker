package com.example.ducker.API

import com.example.ducker.data.Seguido
import retrofit2.Call
import retrofit2.http.*

interface SeguidosAPI {
    @POST("seguir/{id}")
    fun crearSeguidos(@Header("auth") auth : String, @Path("id") id : Int) : Call<APIService.Mensaje>

    @GET("seguidos/{id}")
    fun obtenerSeguidos(@Header("auth") auth: String, @Path("id") id : Int) : Call<List<Seguido>>

    @GET("seguidores/{id}")
    fun obtenerSeguidores(@Header("auth") auth: String, @Path("id") id : Int) : Call<List<Seguido>>

    @GET("seguidores/cantidad/{id}")
    fun obtenerCantidadSeguidores(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>

    @GET("seguidos/cantidad/{id}")
    fun obtenerCantidadSeguidos(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>

    @GET("seguidoscomprobar/{id}")
    fun seguidosComprobar(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>
}