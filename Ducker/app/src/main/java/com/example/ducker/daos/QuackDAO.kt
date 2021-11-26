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
                val quacks : List<Quack> = call.await()
                listaQuacks = quacks
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaQuacks
        }

        suspend fun crearQuack(authKey: String, quack: Quack) : Int {
            var respuesta = 0
            try {
                val call : Call<ResponseBody> = quackAPI.crearQuack(authKey, quack)
                val response = call.awaitResponse()
                respuesta = response.code()
            } catch (exception : Exception){
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerQuackPorId(authKey: String, idQuack : Int) : Quack {
            lateinit var quack : Quack
            try {
                val call : Call<Quack> = quackAPI.obtenerQuackPorId(authKey, idQuack)
                val quackRecibido = call.await()
                quack = quackRecibido
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return quack
        }

        suspend fun eliminarQuack(authKey: String, idQuack: Int) : Int {
            var respuesta = 0
            try {
                val call : Call<ResponseBody> = quackAPI.eliminarQuack(authKey, idQuack)
                val callResponse = call.awaitResponse()
                respuesta = callResponse.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerQuacksPorUsuario(authKey: String, idUsuario: Int) : List<Quack> {
            var listaQuacks : List<Quack> = listOf()
            try {
                val call : Call<List<Quack>> = quackAPI.obtenerQuacksPorUsuario(authKey, idUsuario)
                val quacks = call.await()
                listaQuacks = quacks
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaQuacks
        }
    }
}