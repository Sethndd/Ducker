package com.example.ducker.API

import com.example.ducker.data.Perfil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface PerfilAPI {
    @GET("perfil")
    fun obtener(@Header("auth") auth: String): Call<Perfil>

    @GET("perfil/{id}")
    fun obtener(@Header("auth") auth: String, @Path("id") id : Int): Call<Perfil>
}