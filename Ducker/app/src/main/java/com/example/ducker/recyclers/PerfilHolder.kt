package com.example.ducker.recyclers

import android.app.Activity
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.data.Usuario
import kotlinx.android.synthetic.main.item_perfil.view.*

class PerfilHolder(val view: View):  RecyclerView.ViewHolder(view) {
    protected var authKey = ""

    fun render(usuario: Usuario, auth: String, activity: Activity){
        view.txtNombre.text = usuario.nombrePropio
        view.txtUsuario.text = usuario.nombreUsuario
    }
}