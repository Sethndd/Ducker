package com.example.ducker

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_buscador.*

class Buscador : Botonera() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador)
        aregarListeners()
    }

    private fun aregarListeners() {
        listenerBtnHome(btnMenuPrincipal)
//        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)
    }
}

