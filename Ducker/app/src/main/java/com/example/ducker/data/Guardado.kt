package com.example.ducker.data

import com.google.gson.annotations.SerializedName

data class Guardado (@SerializedName("idGuardado") val idGuardado:Int,
                     @SerializedName("idUsuario") val idUsuario:Int,
                     @SerializedName("idQuack") val IdQueack:Int)