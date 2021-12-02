package com.example.ducker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.Recyclers.QuackAdapter
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.QuackDAO
import com.example.ducker.daos.UsuarioDAO
import com.example.ducker.data.Quack
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perfil_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerfilUsuario : AppCompatActivity() {
    private var authKey = ""
    private var listaQuacks = listOf<Quack>()
    private var idUsuario: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
        idUsuario = bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        recyclerView.layoutManager = LinearLayoutManager(this)

        cargarPerfil()
        cargarQuacks()
//        aregarListeners()

        println()
    }

    private fun cargarPerfil() {
        CoroutineScope(Dispatchers.IO).launch{
            val usuario = UsuarioDAO.obtener(authKey, idUsuario)
            val perfil = PerfilDAO.obtener(authKey, idUsuario)

            runOnUiThread {
                txtNombre.text = usuario.nombrePropio
                txtUsuario.text = "@".plus(usuario.nombreUsuario)

                if (usuario.idUsuario == 0){
                    btnEditarOSeguir.text = "Editar"
                }

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(btnFotoPerfil)

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.bannerRuta))
                    .fit()
                    .centerCrop()
                    .into(btnPortada)
            }
        }
    }

    private fun cargarQuacks() {
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacksPorUsuario(authKey, idUsuario)
            runOnUiThread{
                recyclerView.adapter = QuackAdapter(listaQuacks, authKey, activity)
            }
        }
    }
    private fun aregarListeners() {
        TODO("Not yet implemented")
    }
}