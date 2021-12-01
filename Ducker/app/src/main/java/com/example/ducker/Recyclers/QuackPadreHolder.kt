package com.example.ducker.Recyclers

import android.app.Activity
import android.view.View
import com.example.ducker.R
import com.example.ducker.daos.LikesDAO
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.data.Quack
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_quack.view.*
import kotlinx.android.synthetic.main.item_quack_padre.view.*
import kotlinx.android.synthetic.main.item_quack_padre.view.btnLike
import kotlinx.android.synthetic.main.item_quack_padre.view.fotoPerfil
import kotlinx.android.synthetic.main.item_quack_padre.view.hora
import kotlinx.android.synthetic.main.item_quack_padre.view.nombrePropio
import kotlinx.android.synthetic.main.item_quack_padre.view.nombreUsuario
import kotlinx.android.synthetic.main.item_quack_padre.view.texto
import kotlinx.android.synthetic.main.item_quack_padre.view.txtContadorLikes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class QuackPadreHolder(override val view: View): QuackHolder(view){
    override fun render(quack: Quack, auth: String, activity: Activity){
        super.authKey = auth
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

    override fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            val perfil = PerfilDAO.obtener(authKey, quack.idUsuario)
            val likes = LikesDAO.obtenerCantidadLikesQuack(authKey, quack.id)
            val quackLikeado = LikesDAO.comprobarLike(authKey, quack.id)

            CoroutineScope(Dispatchers.Main).launch{

                if (quackLikeado) {
                    view.btnLike.setImageResource(R.drawable.like)
                } else {
                    view.btnLike.setImageResource(R.drawable.no_like)
                }
                view.txtContadorLikes.text = likes.toString()
                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(view.fotoPerfil)
            }
        }
    }
}