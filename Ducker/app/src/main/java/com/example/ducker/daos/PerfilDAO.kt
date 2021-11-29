package com.example.ducker.daos

import com.example.ducker.API.APIService
import com.example.ducker.API.PerfilAPI
import com.example.ducker.data.Perfil
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.await
import java.lang.Exception

class PerfilDAO {
    companion object {
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val perfilAPI = retrofit.create(PerfilAPI::class.java)

        suspend fun obtener(authKey: String) : Perfil {
            lateinit var perfil: Perfil
            try {
                val call : Call<Perfil> = perfilAPI.obtener(authKey)
                val perfilRecibido = call.await()
                perfil = perfilRecibido
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return perfil
        }
        suspend fun obtener(authKey: String, idUsuario: Int) : Perfil {
            lateinit var perfil: Perfil
            try {
                val call : Call<Perfil> = perfilAPI.obtener(authKey, idUsuario)
                val perfilRecibido = call.await()
                perfil = perfilRecibido
            } catch (exception : Exception) {
                exception.printStackTrace()
            }
            return perfil
        }
    }
}