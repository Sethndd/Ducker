package com.example.ducker.Recyclers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.PerfilUsuario
import com.example.ducker.R
import com.example.ducker.daos.PerfilDAO
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


class QuackAdapter(val listaQuacks: List<Quack>, val auth: String, val activity: Activity): RecyclerView.Adapter<QuackAdapter.QuackHolder>(){
    class QuackHolder(val view: View):  RecyclerView.ViewHolder(view){
        private var authKey = ""

        fun render(quack: Quack, auth: String, activity: Activity){
            authKey = auth

            var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val fechaActual = simpleDateFormat.format(Date())
            val fechaQuack = simpleDateFormat.format(quack.fechaHora)
            if (fechaActual == fechaQuack) {
                simpleDateFormat = SimpleDateFormat("HH:mm")
            }

            view.nombrePropio.text = quack.nombrePropio
            view.nombreUsuario.text = "@".plus(quack.nombreUsuario)
            view.hora.text = simpleDateFormat.format(quack.fechaHora)
            view.texto.text = quack.texto

            cargarFotoPerfil(quack.idUsuario, view.fotoPerfil)

            agregarListeners(view.context, quack.idUsuario)
        }

        private fun agregarListeners(context: Context, idUsuario: Int) {
            view.fotoPerfil.setOnClickListener { abrirPerfil(context, idUsuario) }
            view.nombrePropio.setOnClickListener { abrirPerfil(context, idUsuario) }
            view.nombreUsuario.setOnClickListener { abrirPerfil(context, idUsuario) }
        }

        private fun abrirPerfil(context: Context, idUsuario: Int){
            val intent = Intent(context.applicationContext, PerfilUsuario::class.java)
            intent.putExtra("authKey", authKey)
            intent.putExtra("id", idUsuario.toString())
            context.startActivity(intent)
        }

        private fun cargarFotoPerfil(idUsuario: Int, imagen: ImageView){
            CoroutineScope(Dispatchers.IO).launch {
                val perfil = PerfilDAO.obtener(authKey, idUsuario)
                CoroutineScope(Dispatchers.Main).launch{
                    Picasso.get()
                        .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                        .transform(CyrclePicasso())
                        .into(view.fotoPerfil)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuackHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuackHolder(layoutInflater.inflate(R.layout.item_quack, parent, false))
    }

    override fun onBindViewHolder(holder: QuackHolder, position: Int) {
        holder.render(listaQuacks[position], auth, activity)
    }

    override fun getItemCount(): Int {
        return listaQuacks.size
    }
}