package com.example.ducker

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.recyclers.QuackAdapter
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.QuackDAO
import com.example.ducker.daos.SeguidosDAO
import com.example.ducker.daos.UsuarioDAO
import com.example.ducker.data.Quack
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_perfil_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PerfilUsuario : Botonera() {
    private var idUsuario: Int = 0
    private var listaQuacks = listOf<Quack>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        idUsuario = bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_usuario)

        recyclerView.layoutManager = LinearLayoutManager(this)

        aregarListeners()
        cargarPerfil()
        cargarQuacks()
        cargarSeguidores()
        cargarBtnPerfil()
    }

    private fun aregarListeners() {
        listenerBtnHome(btnMenuPrincipal)
        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)

        linearLayoutSeguidos.setOnClickListener { abrirSeguidosSeguidores("seguidos") }
        linearLayoutSeguidores.setOnClickListener { abrirSeguidosSeguidores("seguidores") }
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
                    agregarListenerEditar()
                }
                else{
                    agregarListenerSeguir()
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

    private fun cargarSeguidores() {
        CoroutineScope(Dispatchers.IO).launch{
            val seguidores = SeguidosDAO.obtenerCantidadSeguidores(authKey, idUsuario)
            val seguidos = SeguidosDAO.obtenerCantidadSeguidos(authKey, idUsuario)

            runOnUiThread {
                txtNumeroSeguidos.text = seguidos.toString()
                txtNumeroSeguidores.text = seguidores.toString()
            }
        }
    }

    private fun cargarQuacks() {
        val activity = this
        CoroutineScope(Dispatchers.IO).launch {
            listaQuacks = QuackDAO.obtenerQuacksPorUsuario(authKey, idUsuario)
            runOnUiThread{
                recyclerView.adapter = QuackAdapter(listaQuacks, authKey, activity, R.layout.item_quack)
            }
        }
    }

    private fun cargarBtnPerfil() {
        if(idUsuario > 0){
            CoroutineScope(Dispatchers.IO).launch {
                val esSeguidor = SeguidosDAO.seguidosComprobar(authKey, idUsuario)
                runOnUiThread{
                    if(esSeguidor){
                        btnEditarOSeguir.text = "Dejar de seguir"
                    }
                }
            }
        }
    }

    private fun agregarListenerSeguir() {
        btnEditarOSeguir.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch{
                val esSeguidor = SeguidosDAO.crearSeguidos(authKey, idUsuario)

                runOnUiThread{
                    if(esSeguidor){
                        btnEditarOSeguir.text = "Dejar de seguir"
                    }
                    else{
                        btnEditarOSeguir.text = "Seguir"
                    }
                }
            }
        }
    }

    private fun agregarListenerEditar(){
        btnEditarOSeguir.setOnClickListener {
            println("Voy a editar")
            //ToDo: abrir ventana de editar perfil
        }
    }

    private fun abrirSeguidosSeguidores(tipo: String){
        val intent : Intent = Intent(this, SeguidosSeguidores::class.java)
        intent.putExtra("authKey", authKey)
        intent.putExtra("tipo", tipo)
        intent.putExtra("id", idUsuario.toString())

        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onBackPressed() {
        if(idUsuario == 0){
            super.onBackPressed()
        }
        else{
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }
}