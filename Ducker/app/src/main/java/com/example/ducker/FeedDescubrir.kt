package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_feed_descubrir.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedDescubrir : AppCompatActivity() {
    private var authKey = ""
    private var listaQuacks = listOf<Quack>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_descubrir)

        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()

        recyclerView.layoutManager = LinearLayoutManager(this)
        agregarListeners()
        obtenerQuacks()
    }


    fun obtenerQuacks(){
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacks(authKey)
            runOnUiThread{
                val adapter = QuackAdapter(listaQuacks, authKey, activity)
                recyclerView.adapter = adapter
            }
        }
    }

    fun agregarListeners(){
        btnSiguiendo.setOnClickListener{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.left_out, R.anim.left_in)
            finish()
        }

        btnNuevoQuack.setOnClickListener {
            val intent : Intent = Intent(this, NuevoQuack::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }


        btnBuscador.setOnClickListener{
            val intent = Intent(this, Buscador::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.left_out, R.anim.left_in)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Feed::class.java)
        startActivity(intent.putExtra("authKey", authKey))
        overridePendingTransition(R.anim.left_out, R.anim.left_in)
        finish()
    }
}