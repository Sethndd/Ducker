package com.example.ducker

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

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
        var id =  bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quack_detalles)

        rvPadres.layoutManager = LinearLayoutManager(this)
        rvHijos.layoutManager = LinearLayoutManager(this)

        cargarDatos(id)
    }

    fun cargarDatos(id: Int){
        val activity = this

        CoroutineScope(Dispatchers.IO).launch{
            val quack: Quack = QuackDAO.obtenerQuackPorId(authKey, id)
            val perfil = PerfilDAO.obtener(authKey, quack.idUsuario)
            val padres = QuackDAO.obtenerPadres(authKey, quack.id)
            val hijos = QuackDAO.obtenerHijos(authKey, quack.id)

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
}