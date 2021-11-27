package com.example.ducker.API

import com.example.ducker.data.Hashtag
import retrofit2.Call
import retrofit2.http.*

interface HashtagAPI {
    @POST("hashtags")
    fun crearHashtag(@Header("auth") auth : String, @Body hashtag : Hashtag) : Call<APIService.Mensaje>

    @GET("hashtags")
    fun obtenerPopulares(@Header("auth") auth: String) : Call<List<Hashtag>>

    @GET("hashtags/quack/{idQuack}")
    fun obtenerHashtagsPorQuack(@Header("auth") auth: String, @Path("idQauck") idQuack : Int) : Call<List<Hashtag>>

    @GET("hashtags/hashtag/{hashtag}")
    fun obtenerHashtagsPorHashtag(@Header("auth") auth: String, @Path("hashtag") hashtag: String) : Call<List<Hashtag>>
}