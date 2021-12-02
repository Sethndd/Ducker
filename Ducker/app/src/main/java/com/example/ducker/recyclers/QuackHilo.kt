package com.example.ducker.recyclers

import android.view.View

class QuackHilo(override val view: View): QuackHolder(view) {
    override fun cargarDatos(){
        cargarLikes()
        cargarNumeroComentarios()
        cargarFotoPerfil()
    }
}