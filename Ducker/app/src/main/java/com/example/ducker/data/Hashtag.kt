package com.example.ducker.data

import com.google.gson.annotations.SerializedName

data class Hashtag (@SerializedName("idHashtag") val idHashtag:Int,
                    @SerializedName("idQuack") val idQuack:Int,
                    @SerializedName("hashtag") val hashtag:String)