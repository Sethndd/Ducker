package com.example.ducker.API

import com.example.ducker.data.Guardado
import retrofit2.Call
import retrofit2.http.*

interface GuardadoAPI {
    @POST("guardados")
    fun crearGuardado(@Header("auth") auth : String, @Body quack : Guardado) : Call<APIService.Mensaje>

    @GET("guardados")
    fun obtenerGuardados(@Header("auth") auth: String) : Call<List<Guardado>>

    @DELETE("guardados/{id}")
    fun eliminarGuardado(@Header("auth") auth: String, @Path("id") id : Int) : Call<APIService.Mensaje>
}