package com.example.ducker.data

import com.google.gson.annotations.SerializedName

data class Seguido (@SerializedName("idSeguidor") val idSeguidor:Int,
                    @SerializedName("idSeguido") val idSeguido:Int)