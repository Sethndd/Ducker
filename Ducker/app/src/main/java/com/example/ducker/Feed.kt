package com.example.ducker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Feed : AppCompatActivity() {
    private var authKey = ""
    private var listaQuacks = listOf<Quack>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

//        publicarQuack()
//        obtenerQuackPorId()
//        eliminarQuack()
//        obtenerQuacksPorUsuario()

        recyclerView.layoutManager = LinearLayoutManager(this)
        agregarListeners()
        obtenerQuacks()
    }

    fun obtenerQuacks(){
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacks(authKey)
            runOnUiThread{
                println(authKey)
                val adapter = QuackAdapter(listaQuacks, authKey, activity)
                recyclerView.adapter = adapter
                adapter.notifyItemInserted(listaQuacks.size)
            }
        }
    }

    fun agregarListeners() {
        btnNuevoQuack.setOnClickListener {
            val intent : Intent = Intent(this, NuevoQuack::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }

        btnDescubrir.setOnClickListener{
            val intent : Intent = Intent(this, FeedDescubrir::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.right_in, R.anim.right_out)
            finish()
        }

        btnBuscador.setOnClickListener{
            val intent = Intent(this, Buscador::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }

//    fun publicarQuack(){
//        val authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"
//        val quack = Quack(0, 6, "a ver jsjsj", 0, Date(), "activo", 0, "Angel", "Angel")
//        CoroutineScope(Dispatchers.IO).launch {
//            println(QuackDAO.crearQuack(authKey, quack))
//        }
//    }
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