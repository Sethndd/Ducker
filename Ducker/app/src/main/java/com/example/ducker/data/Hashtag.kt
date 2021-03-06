package com.example.ducker.data

import com.google.gson.annotations.SerializedName

data class Hashtag (@SerializedName("id") val idHashtag:Int,
                    @SerializedName("idQuack") val idQuack:Int,
                    @SerializedName("hashtag") val hashtag:String) {
    override fun toString(): String {
        return hashtag
    }
}