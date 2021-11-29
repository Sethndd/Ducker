package com.example.ducker.Recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
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
        fun render(quack: Quack, auth: String, activity: Activity){
            var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
            val fechaActual = simpleDateFormat.format(Date())
            val fechaQuack = simpleDateFormat.format(quack.fechaHora)
            if (fechaActual == fechaQuack) {
                simpleDateFormat = SimpleDateFormat("HH:mm")
            }

            cargarFotoPerfil(auth, quack.idUsuario,view.fotoPerfil)
//
//            Picasso.get()
//                .load(Rutas.APIIMAGENES.plus("/imagenes/imagen_1638188169566.jpg"))
//                .transform(CyrclePicasso())
//                .into(view.fotoPerfil)

            view.nombrePropio.text = quack.nombrePropio
            view.nombreUsuario.text = "@".plus(quack.nombreUsuario)
            view.hora.text = simpleDateFormat.format(quack.fechaHora)
            view.texto.text = quack.texto
        }

        fun cargarFotoPerfil(auth: String, idUsuario: Int, imagen: ImageView){
            CoroutineScope(Dispatchers.IO).launch {
                val perfil = PerfilDAO.obtener(auth, idUsuario)
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