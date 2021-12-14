package com.example.ducker.API

import com.example.ducker.API.bodyClases.Login
import com.example.ducker.API.bodyClases.Token
import com.example.ducker.data.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthenticationAPI {
    @POST("/login/")
    fun iniciarSesion(@Body login: Login): Call<Token>

    @POST("/registrarse/")
    fun registrarse(@Body usuario: Usuario): Call<APIService.Mensaje>

    @GET("validarToken")
    fun validarAuth(@Header("auth") auth: String): Call<APIService.Mensaje>
}