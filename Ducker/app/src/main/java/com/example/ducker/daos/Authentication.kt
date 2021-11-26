package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.AuthenticationAPI
import com.example.ducker.API.bodyClases.Login
import com.example.ducker.API.bodyClases.Token
import com.example.ducker.data.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.awaitResponse
import java.lang.Exception

class Authentication {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val UserAPI = retrofit.create(AuthenticationAPI::class.java)

        suspend fun iniciarSesion(login: Login): String {
            var authKey = ""
            try{
                val call: Call<Token> = UserAPI.iniciarSesion(login)
                val respuesta = call.awaitResponse()

                if(respuesta.isSuccessful){
                    val token: Token? = respuesta.body()
                    authKey = token?.token.toString()
                }
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }

            return authKey
        }
        suspend fun registrar(usuario: Usuario): Int{
            var codigo = 0

            try {
                val call: Call<APIService.Mensaje> = UserAPI.registrarse(usuario)
                val respuesta = call.awaitResponse()

                codigo = respuesta.code()
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }

            return codigo
        }
    }
}