package com.example.ducker.API

import com.example.ducker.util.Rutas
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.annotations.SerializedName

class APIService {
    companion object{
        fun obtenerRetroFit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Rutas.LOCALHOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    inner class Mensaje (@SerializedName("Mensaje") val mensaje : String)

}