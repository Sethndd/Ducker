package com.example.ducker.recyclers

import android.app.Activity
import android.view.View
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.item_quack_padre.view.viewArriba
import kotlinx.android.synthetic.main.item_quack_hijo.view.viewAbajo

class QuackHilo(override val view: View): QuackHolder(view) {
    override fun render(quack: Quack, auth: String, activity: Activity, vistaHilos: String) {
        when(vistaHilos){
            "arriba" ->
                view.viewArriba.visibility = View.INVISIBLE
            "abajo" ->
                view.viewAbajo.visibility = View.INVISIBLE
        }
        super.render(quack, auth, activity, vistaHilos)
    }

    override fun cargarDatos(){
        cargarLikes()
        cargarNumeroComentarios()
        cargarFotoPerfil()
    }
}