package com.example.ducker.API

import com.example.ducker.data.Seguido
import retrofit2.Call
import retrofit2.http.*

interface SeguidosAPI {
    @POST("seguidos")
    fun crearSeguidos(@Header("auth") auth : String, @Body seguido : Seguido) : Call<APIService.Mensaje>

    @GET("seguidos")
    fun obtenerSeguidos(@Header("auth") auth: String) : Call<List<Seguido>>

    @GET("seguidores")
    fun obtenerSeguidores(@Header("auth") auth: String) : Call<List<Seguido>>

    @GET("seguidos/{id}")
    fun obtenerSeguidosUsuario(@Header("auth") auth: String, @Path("id") id : Int) : Call<List<Seguido>>

    @GET("seguidores/{id}")
    fun obtenerSeguidoresUsuario(@Header("auth") auth: String, @Path("id") id : Int) : Call<List<Seguido>>

    @GET("seguidores/cantidad/{id}")
    fun obtenerCantidadSeguidores(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>

    @GET("seguidos/cantidad/{id}")
    fun obtenerCantidadSeguidos(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>

    @GET("seguidoscomprobar")
    fun seguidosComprobar(@Header("auth") auth: String) : Call<APIService.Mensaje>
}