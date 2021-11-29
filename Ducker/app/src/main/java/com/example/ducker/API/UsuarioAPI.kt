package com.example.ducker.API

import com.example.ducker.data.Usuario
import retrofit2.Call
import retrofit2.http.*

interface UsuarioAPI {
    @GET("usuarios")
    fun obtener(@Header("auth") auth: String): Call<List<Usuario>>

    @GET("usuarios/{id}")
    fun obtener(@Header("auth") auth: String, @Path("id") id : Int): Call<Usuario>

    @PATCH("usuarios/{id}")
    fun actualizar(@Header("auth") auth: String, @Path("id") id : Int): Call<APIService.Mensaje>

    @DELETE("usuarios/{id}")
    fun eliminar(@Header("auth") auth: String, @Path("id") id : Int): Call<APIService.Mensaje>
}