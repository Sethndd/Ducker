package com.example.ducker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.API.APIService
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        obtenerQuacks(this)
    }

    fun obtenerRetroFit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.65:1806/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun obtenerQuacks(context: Context){
        val authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"
        val apiService = obtenerRetroFit().create(APIService::class.java)

        val call: Call<List<Quack>> = apiService.getPosts(authKey)
        call.enqueue(object : Callback<List<Quack>>{
            override fun onResponse(call: Call<List<Quack>>, response: Response<List<Quack>>) {
                if(response.isSuccessful){
                    val quacks: List<Quack>? = response.body()
                    if (quacks != null) {
                        iniciarRecycler(quacks)
                    }
                }
                else{
                    Toast.makeText(context, response.message().toString(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Quack>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                return
            }

        })
    }

    fun iniciarRecycler(listaQuacks: List<Quack>) {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = QuackAdapter(listaQuacks, this)
    }
}