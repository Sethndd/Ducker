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

        recyclerView.layoutManager = LinearLayoutManager(this)
        agregarListeners()
        obtenerQuacks()
    }

    fun obtenerQuacks(){
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacksSeguidos(authKey)
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

}