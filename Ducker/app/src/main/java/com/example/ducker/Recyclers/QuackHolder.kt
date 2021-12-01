package com.example.ducker.Recyclers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.PerfilUsuario
import com.example.ducker.QuackDetalles
import com.example.ducker.QuackRespuesta
import com.example.ducker.daos.LikesDAO
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_quack.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

open class QuackHolder(open val view: View):  RecyclerView.ViewHolder(view){
    protected var authKey = ""
    protected lateinit var quack: Quack

    open fun render(quack: Quack, auth: String, activity: Activity){
        this.authKey = auth
        this.quack = quack

        var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
        val fechaActual = simpleDateFormat.format(Date())
        val fechaQuack = simpleDateFormat.format(quack.fechaHora)
        if (fechaActual == fechaQuack) {
            simpleDateFormat = SimpleDateFormat("HH:mm")
            simpleDateFormat.timeZone = TimeZone.getTimeZone("America/Mexico_City")
        }

        view.nombrePropio.text = quack.nombrePropio
        view.nombreUsuario.text = "@".plus(quack.nombreUsuario)
        view.hora.text = simpleDateFormat.format(quack.fechaHora)
        view.texto.text = quack.texto

        cargarDatos()

        agregarListeners()
    }

    protected open fun agregarListeners() {
        view.fotoPerfil.setOnClickListener { abrirPerfil() }
        view.nombrePropio.setOnClickListener { abrirPerfil() }
        view.nombreUsuario.setOnClickListener { abrirPerfil() }

        view.btnComentario.setOnClickListener { abrirResponderQuack() }
        view.txtContadorComentarios.setOnClickListener { abrirResponderQuack() }

        view.setOnClickListener { abrirQuack() }
    }

    protected open fun abrirPerfil(){
        val intent = Intent(view.context.applicationContext, PerfilUsuario::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("id", quack?.idUsuario.toString())
        view.context.startActivity(intent)
    }

    protected open fun abrirQuack(){
        val intent = Intent(view.context.applicationContext, QuackDetalles::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("id", quack?.id.toString())
        view.context.startActivity(intent)
    }

    protected open fun abrirResponderQuack() {
        val intent = Intent(view.context.applicationContext, QuackRespuesta::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("id", quack?.id.toString())
        view.context.startActivity(intent)
    }

    protected open fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            val perfil = PerfilDAO.obtener(authKey, quack.idUsuario)
            val likes = LikesDAO.obtenerCantidadLikesQuack(authKey, quack.id)
            var padre: Quack? = null

            if(quack.quackPadre > 0){
                padre = QuackDAO.obtenerQuackPorId(authKey, quack.quackPadre)
            }

            CoroutineScope(Dispatchers.Main).launch{
                view.txtContadorLikes.text = likes.toString()
                if(quack.quackPadre > 0){
                    view.txtEtiqueta.visibility = View.VISIBLE
                    view.txtUsuarioPadre.visibility = View.VISIBLE
                    view.txtUsuarioPadre.text = "@".plus(padre?.nombreUsuario)
                }

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(view.fotoPerfil)
            }
        }
    }
}