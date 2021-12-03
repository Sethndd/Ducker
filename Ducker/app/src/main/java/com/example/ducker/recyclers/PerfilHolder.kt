package com.example.ducker.recyclers

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.PerfilUsuario
import com.example.ducker.R
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.SeguidosDAO
import com.example.ducker.data.Usuario
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_perfil.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerfilHolder(val view: View):  RecyclerView.ViewHolder(view) {
    protected var authKey = ""
    protected lateinit var usuario: Usuario

    fun render(usuario: Usuario, auth: String, activity: Activity){
        view.txtNombre.text = usuario.nombrePropio
        view.txtUsuario.text = "@".plus(usuario.nombreUsuario)
        this.usuario = usuario
        this.authKey = auth

        cargarFotoPerfil()
        cargarBtnSeguir()

        view.setOnClickListener {
            val intent = Intent(view.context.applicationContext, PerfilUsuario::class.java)
            intent.putExtra("authKey", authKey)
            intent.putExtra("id", usuario.idUsuario.toString())

            view.context.startActivity(intent)
            (view.context as Activity).overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        view.btnSeguir.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                val esSeguidor = SeguidosDAO.crearSeguidos(authKey, usuario.idUsuario)

                CoroutineScope(Dispatchers.Main).launch{
                    if(esSeguidor){
                        view.btnSeguir.text = "Dejar de seguir"
                    }
                    else{
                        view.btnSeguir.text = "Seguir"
                    }
                }
            }
        }
    }

    private fun cargarFotoPerfil() {
        CoroutineScope(Dispatchers.IO).launch {
            val perfil = PerfilDAO.obtener(authKey, usuario.idUsuario)
            CoroutineScope(Dispatchers.Main).launch{
                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(view.btnFotoPerfil)
            }
        }
    }

    private fun cargarBtnSeguir(){
        CoroutineScope(Dispatchers.IO).launch{
            val esSeguidor = SeguidosDAO.seguidosComprobar(authKey, usuario.idUsuario)
            CoroutineScope(Dispatchers.Main).launch{
                if(esSeguidor){
                    view.btnSeguir.text = "Dejar de seguir"
                }
                else{
                    view.btnSeguir.text = "Seguir"
                }
            }
        }
    }
}