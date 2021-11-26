package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.QuackAPI
import com.example.ducker.data.Quack
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.*
import java.lang.Exception

class QuackDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val quackAPI = retrofit.create(QuackAPI::class.java)

        suspend fun obtenerQuacks(authKey : String): List<Quack> {
            var listaQuacks : List<Quack> = listOf()
            try {
                val call : Call<List<Quack>> = quackAPI.obtenerQuacks(authKey)
                val quacks : List<Quack>? = call.awaitResponse().body()
                if(quacks != null){
                    listaQuacks = quacks
                }
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaQuacks
        }

        suspend fun crearQuack(authKey: String, quack: Quack) : String {
            var respuesta = ""
            try {
                val call : Call<ResponseBody> = quackAPI.crearQuack(authKey, quack)
                val responseBody : ResponseBody = call.await()
                val response = responseBody.string()
                val gson = Gson()
                val mensaje = gson.fromJson<APIService.Mensaje>(response, APIService.Mensaje::class.java)
                respuesta = mensaje.mensaje
            } catch (exception : Exception){
                exception.printStackTrace()
            }
            return respuesta
        }
    }

}