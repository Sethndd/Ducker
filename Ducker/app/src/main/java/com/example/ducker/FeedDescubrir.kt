package com.example.ducker

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.recyclers.QuackAdapter
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_feed_descubrir.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FeedDescubrir : Botonera() {
    private var listaQuacks = listOf<Quack>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_descubrir)

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
        listenerBtnHome(btnMenuPrincipal)
        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)

        btnSiguiendo.setOnClickListener{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.left_out, R.anim.left_in)
            finish()
        }
    }
}