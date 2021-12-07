package com.example.ducker

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.recyclers.QuackAdapter
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Feed : Botonera() {
    private var listaQuacks = listOf<Quack>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        recyclerView.layoutManager = LinearLayoutManager(this)
        agregarListeners()
        obtenerQuacks()
    }

    fun agregarListeners() {
//        listenerBtnHome(btnMenuPrincipal)
        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)

        btnDescubrir.setOnClickListener{
            val intent : Intent = Intent(this, FeedDescubrir::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.right_in, R.anim.right_out)
            finish()
        }
    }

    fun obtenerQuacks(){
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacksSeguidos(authKey)
            runOnUiThread{
                val adapter = QuackAdapter(listaQuacks, authKey, activity, R.layout.item_quack)
                recyclerView.adapter = adapter
            }
        }
    }

    override fun onBackPressed() {
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        moveTaskToBack(true)
    }
}