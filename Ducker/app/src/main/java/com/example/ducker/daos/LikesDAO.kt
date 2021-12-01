package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.LikesAPI
import com.example.ducker.data.Like
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception

class LikesDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val likesAPI = retrofit.create(LikesAPI::class.java)

        suspend fun crearLikes(authKey : String, like : Like) :Boolean {
            var respuesta = false
            try {
                val call : Call<APIService.Mensaje> = likesAPI.crearLikes(authKey, like)
                val response = call.await()
                if (response.mensaje.toInt() == 1){
                    respuesta = true
                }
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun obtenerCantidadLikesQuack(authKey: String, id : Int) : Int{
            var likes = 0
            try {
                val call : Call<APIService.Mensaje> = likesAPI.obtenerCantidadLikesQuack(authKey, id)
                val response : APIService.Mensaje = call.await()
                likes = response.mensaje.toInt()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return likes
        }

        suspend fun eliminarLikes(authKey: String, idLike : Int) : Int {
            var respuesta = 0
            try {
                val call : Call<APIService.Mensaje> = likesAPI.eliminarLikes(authKey, idLike)
                val response = call.awaitResponse()
                respuesta = response.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respuesta
        }

        suspend fun comprobarLike(authKey: String, id: Int) : Boolean {
            var respuesta = false
            try {
                val call : Call<APIService.Mensaje> = likesAPI.comprobarLike(authKey, id)
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