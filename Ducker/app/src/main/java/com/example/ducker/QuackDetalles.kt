package com.example.ducker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_quack_detalles.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

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

            idUsuario = quack.idUsuario

            runOnUiThread {
//                var simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")
                var simpleDateFormat = SimpleDateFormat("HH:mm")

//                if (simpleDateFormat.format(Date()) == simpleDateFormat.format(quack.fechaHora)) {
//                    simpleDateFormat = SimpleDateFormat("HH:mm")
//                }

                nombrePropio.text = quack.nombrePropio
                nombreUsuario.text = "@".plus(quack.nombreUsuario)
                hora.text = simpleDateFormat.format(quack.fechaHora)
                texto.text = quack.texto

                rvPadres.adapter = QuackAdapter(padres, authKey, activity)
                rvHijos.adapter = QuackAdapter(hijos, authKey, activity)

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
    }

    private fun abrirPerfil(context: Context){
        val intent = Intent(context.applicationContext, PerfilUsuario::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("id", idUsuario.toString())
        context.startActivity(intent)
    }
}