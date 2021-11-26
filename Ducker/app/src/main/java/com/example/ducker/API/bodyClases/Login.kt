package com.example.ducker.API.bodyClases

import com.google.gson.annotations.SerializedName

data class Login(@SerializedName("correo") val correo: String,
                 @SerializedName("contrasena") val contrasena: String)
