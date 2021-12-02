package com.example.ducker

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.recyclers.QuackAdapter
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
        listenerBtnHome(btnMenuPrincipal)
        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
//        listenerBtnPerfil(btnPerfil)
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}