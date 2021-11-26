package com.example.ducker

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var listaQuacks = listOf<Quack>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        obtenerQuacks(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    fun obtenerQuacks(context: Context){
        val authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacks(authKey)
            runOnUiThread{
                recyclerView.adapter = QuackAdapter(listaQuacks, activity)
            }
        }
    }

}