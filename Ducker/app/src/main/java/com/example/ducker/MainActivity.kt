package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ducker.data.Quack

class MainActivity : AppCompatActivity() {
    private var authKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        obtenerQuacks(this)
//        publicarQuack()
//        obtenerQuackPorId()
//        eliminarQuack()
//        obtenerQuacksPorUsuario()

        //ToDo Obtener de algun lado del dispositivo (SQLite??????)
//        authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"

        if(authKey.equals("")){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
        }

        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
    }

//    fun obtenerQuackPorId(){
//        val authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"
//        CoroutineScope(Dispatchers.IO).launch {
//            val quack = QuackDAO.obtenerQuackPorId(authKey, 6)
//            println(quack.texto)
//        }
//    }

//    fun eliminarQuack(){
//        val authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"
//        CoroutineScope(Dispatchers.IO).launch {
//            println(QuackDAO.eliminarQuack(authKey, 7))
//        }
//    }

//    fun obtenerQuacksPorUsuario(){
//        val authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"
//        CoroutineScope(Dispatchers.IO).launch {
//            val listaQuacks = QuackDAO.obtenerQuacksPorUsuario(authKey, 6)
//            for (quack in listaQuacks) {
//                println(quack.texto)
//            }
//        }
//    }
}