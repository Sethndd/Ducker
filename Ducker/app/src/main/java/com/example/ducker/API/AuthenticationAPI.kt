package com.example.ducker.API

import com.example.ducker.API.bodyClases.Login
import com.example.ducker.API.bodyClases.Token
import com.example.ducker.data.Usuario
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationAPI {
    @POST("login")
    fun iniciarSesion(@Body login: Login): Call<Token>

    @POST("register")
    fun registrarse(@Body usuario: Usuario): Call<APIService.Mensaje>

}