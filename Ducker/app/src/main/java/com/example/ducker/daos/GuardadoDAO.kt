package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.GuardadoAPI
import com.example.ducker.data.Guardado
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception

class GuardadoDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val guardadoAPI = retrofit.create(GuardadoAPI::class.java)

        suspend fun crearGuardado(authKey : String, guardado : Guardado) :Int {
            var respuesta = 0
            try {
                val call : Call<APIService.Mensaje> = guardadoAPI.crearGuardado(authKey, guardado)
                val response = call.awaitResponse()
                respuesta = response.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerGuardados(authKey: String) : List<Guardado>{
            var listaGuardados : List<Guardado> = listOf()
            try {
                val call : Call<List<Guardado>> = guardadoAPI.obtenerGuardados(authKey)
                val guardados : List<Guardado> = call.await()
                listaGuardados = guardados
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaGuardados
        }

        suspend fun eliminarGuardado(authKey: String, idGuardado : Int) : Int {
            var respeusta = 0
            try {
                var call : Call<APIService.Mensaje> = guardadoAPI.eliminarGuardado(authKey, idGuardado)
                val response = call.awaitResponse()
                respeusta = response.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respeusta
        }
    }
}