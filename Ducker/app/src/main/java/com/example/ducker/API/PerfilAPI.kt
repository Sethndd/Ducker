package com.example.ducker.API

import com.example.ducker.data.Perfil
import retrofit2.Call
import retrofit2.http.*

interface PerfilAPI {
    @GET("perfil")
    fun obtener(@Header("auth") auth: String): Call<Perfil>

    @PATCH("perfil")
    fun actualizar(@Header("auth") auth: String): Call<APIService.Mensaje>

    @GET("perfil/{id}")
    fun obtener(@Header("auth") auth: String, @Path("id") id : Int): Call<Perfil>

}