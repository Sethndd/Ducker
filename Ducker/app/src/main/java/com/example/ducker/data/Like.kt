package com.example.ducker.data

import com.google.gson.annotations.SerializedName

data class Like (@SerializedName("idLike")val idLike:Int,
                 @SerializedName("idQuack") val idQuack:Int,
                 @SerializedName("idUsuario") val idUsuario:Int)