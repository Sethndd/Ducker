package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.UsuarioAPI
import com.example.ducker.data.Usuario
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import java.lang.Exception

class UsuarioDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val usuarioAPI = retrofit.create(UsuarioAPI::class.java)

        suspend fun obtener(authKey : String): List<Usuario>{
            lateinit var listaUsuarios : List<Usuario>
            try{
                val call: Call<List<Usuario>> = usuarioAPI.obtener(authKey)
                val usuarios: List<Usuario> = call.await()
                listaUsuarios = usuarios
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
            return listaUsuarios
        }

        suspend fun obtener(authKey : String, id: Int): Usuario {
            lateinit var usuario: Usuario
            try{
                val call: Call<Usuario> = usuarioAPI.obtener(authKey, id)
                val respuesta: Usuario = call.await()
                usuario = respuesta
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
            return usuario
        }

        suspend fun actualizar(authKey : String, id: Int): String {
            var mensaje = ""
            try{
                val call: Call<APIService.Mensaje> = usuarioAPI.actualizar(authKey, id)
                val respuesta: APIService.Mensaje = call.await()
                mensaje = respuesta.mensaje
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
            return mensaje
        }

        suspend fun eliminar(authKey : String, id: Int): String {
            var mensaje = ""
            try{
                val call: Call<APIService.Mensaje> = usuarioAPI.eliminar(authKey, id)
                val respuesta: APIService.Mensaje = call.await()
                mensaje = respuesta.mensaje
            }
            catch (exception: Exception){
                exception.printStackTrace()
            }
            return mensaje
        }

    }
}