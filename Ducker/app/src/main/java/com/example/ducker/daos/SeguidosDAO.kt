package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.SeguidosAPI
import com.example.ducker.data.Seguido
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception

class SeguidosDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val seguidosAPI = retrofit.create(SeguidosAPI::class.java)

        suspend fun crearSeguidos(authKey : String, seguido : Seguido) :Int {
            var respuesta = 0
            try {
                val call : Call<APIService.Mensaje> = seguidosAPI.crearSeguidos(authKey, seguido)
                val response = call.awaitResponse()
                respuesta = response.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerSeguidos(authKey: String) : List<Seguido>{
            var listaSeguidos : List<Seguido> = listOf()
            try {
                val call : Call<List<Seguido>> = SeguidosDAO.seguidosAPI.obtenerSeguidos(authKey)
                val seguidos : List<Seguido> = call.await()
                listaSeguidos = seguidos
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaSeguidos
        }

        suspend fun obtenerSeguidores(authKey: String) : List<Seguido>{
            var listaSeguidores : List<Seguido> = listOf()
            try {
                val call : Call<List<Seguido>> = SeguidosDAO.seguidosAPI.obtenerSeguidores(authKey)
                val seguidores : List<Seguido> = call.await()
                listaSeguidores = seguidores
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaSeguidores
        }

        suspend fun eliminarSeguidos(authKey: String, idSeguidos : Int) : Int {
            var respuesta = 0
            try {
                var call : Call<APIService.Mensaje> = seguidosAPI.eliminarSeguidos(authKey, idSeguidos)
                val response = call.awaitResponse()
                respuesta = response.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerSeguidosUsuario(authKey: String, id : Int) : List<Seguido>{
            var listaSeguidosUsuario : List<Seguido> = listOf()
            try {
                val call : Call<List<Seguido>> = seguidosAPI.obtenerSeguidosUsuario(authKey, id)
                listaSeguidosUsuario = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaSeguidosUsuario
        }

        suspend fun obtenerSeguidoresUsuario(authKey: String, id : Int) : List<Seguido>{
            var listaSeguidoresUsuario : List<Seguido> = listOf()
            try {
                val call : Call<List<Seguido>> = seguidosAPI.obtenerSeguidoresUsuario(authKey, id)
                listaSeguidoresUsuario = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaSeguidoresUsuario
        }
    }
}