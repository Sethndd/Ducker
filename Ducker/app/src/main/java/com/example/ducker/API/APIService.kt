package com.example.ducker.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.annotations.SerializedName

class APIService {
    companion object{
        fun obtenerRetroFit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://192.168.1.65:1806/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    inner class Mensaje (@SerializedName("Mensaje") val mensaje : String)

}