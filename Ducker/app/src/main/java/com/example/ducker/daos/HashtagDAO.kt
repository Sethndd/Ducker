package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.HashtagAPI
import com.example.ducker.data.Hashtag
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.awaitResponse
import java.lang.Exception

class HashtagDAO {
    companion object {
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val hashtagAPI = retrofit.create(HashtagAPI::class.java)

        suspend fun crearhashtag(authKey : String, hashtag : Hashtag) : Int{
            var respeusta = 0
            try {
                val call : Call<APIService.Mensaje> = hashtagAPI.crearHashtag(authKey, hashtag)
                val response = call.awaitResponse()
                respeusta = response.code()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return respeusta
        }

        suspend fun obtenerHashtagsPopulares(authKey: String) : List<Hashtag> {
            var listaHashtags : List<Hashtag> = listOf()
            try {
                val call : Call<List<Hashtag>> = hashtagAPI.obtenerPopulares(authKey)
                listaHashtags = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaHashtags
        }

        suspend fun obtenerHashtagsPorQuack(authKey: String, idQuack : Int) : List<Hashtag>{
            var listaHashtags : List<Hashtag> = listOf()
            try {
                val call : Call<List<Hashtag>> = hashtagAPI.obtenerHashtagsPorQuack(authKey, idQuack)
                listaHashtags = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaHashtags
        }

        suspend fun obtenerHashtagsPorHashtag(authKey: String, hashtag: String) : List<Hashtag> {
            var listaHashtags : List<Hashtag> = listOf()
            try {
                val call: Call<List<Hashtag>> = hashtagAPI.obtenerHashtagsPorHashtag(authKey, hashtag)
                listaHashtags = call.await()
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return listaHashtags
        }
    }
}