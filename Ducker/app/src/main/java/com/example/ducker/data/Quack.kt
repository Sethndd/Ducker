package com.example.ducker.data

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class Quack (@SerializedName("id") val id:Int,
                  @SerializedName("idUsuario") val idUsuario:Int,
                  @SerializedName("texto")  val texto:String,
                  @SerializedName("quackPadre") val quackPadre:Int,
                  @SerializedName("fechaHora") val fechaHora:String,
                  @SerializedName("estado") val estado:String,
                  @SerializedName("idAdjunto") val idAdjunto: Int,
                  @SerializedName("nombrePropio") val nombrePropio:String,
                  @SerializedName("nombreUsuario")  val nombreUsuario:String)