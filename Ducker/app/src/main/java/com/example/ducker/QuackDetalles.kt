package com.example.ducker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.daos.LikesDAO
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Like
import com.example.ducker.recyclers.QuackAdapter
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_quack_detalles.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class QuackDetalles : AppCompatActivity() {
    private var authKey = ""
    private var id : Int = 0
    private var idUsuario : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
        id =  bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quack_detalles)

        rvPadres.layoutManager = LinearLayoutManager(this)
        rvHijos.layoutManager = LinearLayoutManager(this)

        cargarDatos(id)
        agregarListeners()
    }

    fun cargarDatos(id: Int){
        val activity = this

        CoroutineScope(Dispatchers.IO).launch{
            val quack = QuackDAO.obtenerQuackPorId(authKey, id)
            val perfil = PerfilDAO.obtener(authKey, quack.idUsuario)
            val padres = QuackDAO.obtenerPadres(authKey, quack.id)
            val hijos = QuackDAO.obtenerHijos(authKey, quack.id)
            val likes = LikesDAO.obtenerCantidadLikesQuack(authKey, quack.id)
            val quackLikeado = LikesDAO.comprobarLike(authKey, quack.id)

            idUsuario = quack.idUsuario

            runOnUiThread {
                var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                if (simpleDateFormat.format(Date()) == simpleDateFormat.format(quack.fechaHora)) {
                    simpleDateFormat = SimpleDateFormat("HH:mm")
                }

                if (quackLikeado) {
                    btnLike.setImageResource(R.drawable.like)
                } else {
                    btnLike.setImageResource(R.drawable.no_like)
                }

                nombrePropio.text = quack.nombrePropio
                nombreUsuario.text = "@".plus(quack.nombreUsuario)
                hora.text = simpleDateFormat.format(quack.fechaHora)
                texto.text = quack.texto
                txtContadorLikes.text = likes.toString()

                rvPadres.adapter = QuackAdapter(padres, authKey, activity, R.layout.item_quack_padre)
                rvHijos.adapter = QuackAdapter(hijos, authKey, activity, R.layout.item_quack_hijo)

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(fotoPerfil)
            }
        }
    }

    private fun agregarListeners() {
        fotoPerfil.setOnClickListener { abrirPerfil(this) }
        nombrePropio.setOnClickListener { abrirPerfil(this) }
        nombreUsuario.setOnClickListener { abrirPerfil(this) }

        btnLike.setOnClickListener { crearLike() }
        btnComentario.setOnClickListener { abrirResponderQuack(this) }
        txtContadorComentarios.setOnClickListener { abrirResponderQuack(this) }
    }

    private fun crearLike(){
        CoroutineScope(Dispatchers.IO).launch {
            LikesDAO.crearLikes(authKey, Like(0, id, 0))
        }
        cargarDatos(id)
    }

    private fun abrirPerfil(context: Context){
        val intent = Intent(context.applicationContext, PerfilUsuario::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("id", idUsuario.toString())

        context.startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun abrirResponderQuack(context: Context) {
        val intent = Intent(context.applicationContext, QuackRespuesta::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("id", id.toString())

        context.startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}