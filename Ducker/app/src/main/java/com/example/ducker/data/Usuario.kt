package com.example.ducker.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class Usuario (@SerializedName("id") val idUsuario:Int,
                    @SerializedName("correo") val correo:String,
                    @SerializedName("contrasena") val contrasena:String,
                    @SerializedName("nombrePropio") val nombrePropio:String,
                    @SerializedName("nombreUsuario") val nombreUsuario:String,
                    @SerializedName("fechaNacimiento") val fechaNacimiento:String) {
    override fun toString(): String {
        return '@' +nombreUsuario
    }
}