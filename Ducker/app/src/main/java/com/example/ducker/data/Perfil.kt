package com.example.ducker.data

import com.google.gson.annotations.SerializedName

data class Perfil (@SerializedName("idUsuario") val idUsuario:Int,
                   @SerializedName("imgRuta") val imagenRuta:String,
                   @SerializedName("bannerRuta")  val bannerRuta:String)