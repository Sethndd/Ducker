package com.example.ducker.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIService {
    companion object{
        fun obtenerRetroFit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://192.168.0.11:1806/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}