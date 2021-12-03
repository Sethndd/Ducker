package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.SeguidosAPI
import com.example.ducker.data.Seguido
import com.example.ducker.data.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception

class SeguidosDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val seguidosAPI = retrofit.create(SeguidosAPI::class.java)

        suspend fun crearSeguidos(authKey : String, id: Int) :Boolean {
            var respuesta = false
            try {
                val call : Call<APIService.Mensaje> = seguidosAPI.crearSeguidos(authKey, id)
                val response = call.await()
                if (response.mensaje.toInt() == 1){
                    respuesta = true
                }
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerSeguidosUsuario(authKey: String, id : Int) : List<Usuario>{
            lateinit var usuarios: List<Usuario>
            try {
                val call : Call<List<Usuario>> = seguidosAPI.obtenerSeguidos(authKey, id)
                usuarios = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return usuarios
        }

        suspend fun obtenerSeguidoresUsuario(authKey: String, id : Int) : List<Usuario>{
            lateinit var usuarios: List<Usuario>
            try {
                val call : Call<List<Usuario>> = seguidosAPI.obtenerSeguidores(authKey, id)
                usuarios = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return usuarios
        }

        suspend fun obtenerCantidadSeguidores(authKey: String, idSeguidos : Int) : Int {
            var respuesta = 0
            try {
                val call : Call<APIService.Mensaje> = seguidosAPI.obtenerCantidadSeguidores(authKey, idSeguidos)
                val response : APIService.Mensaje = call.await()
                respuesta = response.mensaje.toInt()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerCantidadSeguidos(authKey: String, idSeguidos : Int) : Int {
            var respuesta = 0
            try {
                val call : Call<APIService.Mensaje> = seguidosAPI.obtenerCantidadSeguidos(authKey, idSeguidos)
                val response : APIService.Mensaje = call.await()
                respuesta = response.mensaje.toInt()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun seguidosComprobar(authKey: String, id: Int) : Boolean {
            var respuesta = false
            try {
                val call : Call<APIService.Mensaje> = seguidosAPI.seguidosComprobar(authKey, id)
                val response = call.await()
                if (response.mensaje.toInt() == 1){
                    respuesta = true
                }
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }
    }
}