package com.example.ducker.API

import com.example.ducker.data.Like
import retrofit2.Call
import retrofit2.http.*

interface LikesAPI {
    @POST("likes")
    fun crearLikes(@Header("auth") auth : String, @Body like : Like) : Call<APIService.Mensaje>

    @GET("likes/{id}")
    fun obtenerCantidadLikesQuack(@Header("auth") auth: String, @Path("id") id : Int) : Call<List<Like>>

    @DELETE("likes/{id}")
    fun eliminarLikes(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>
}