package com.example.ducker.daos

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.API.APIService
import com.example.ducker.API.QuackAPI
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*

class QuackDAO {
    companion object{
        private val retrofit : Retrofit = APIService.obtenerRetroFit()
        private val quackAPI = retrofit.create(QuackAPI::class.java)

        suspend fun obtenerQuacks(authKey : String): List<Quack> {
            var listaQuacks : List<Quack> = listOf()
            val call : Call<List<Quack>> = quackAPI.getPosts(authKey)
            val quacks : List<Quack>? = call.awaitResponse().body()
            if(quacks != null){
                listaQuacks = quacks
            }
            return listaQuacks
        }
    }

}