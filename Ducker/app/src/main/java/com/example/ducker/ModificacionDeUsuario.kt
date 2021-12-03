package com.example.ducker

import android.os.Bundle
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.UsuarioDAO
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_modificacion_de_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModificacionDeUsuario : Botonera() {
    var idUsuario = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        idUsuario = bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificacion_de_usuario)

        cargarPerfil()
        agregarListeners()
    }

    private fun cargarPerfil() {
        CoroutineScope(Dispatchers.IO).launch{
            val usuario = UsuarioDAO.obtener(authKey, idUsuario)
            val perfil = PerfilDAO.obtener(authKey, idUsuario)

            runOnUiThread {
                inputNombre.setText(usuario.nombrePropio)
                inputUsuario.setText("@".plus(usuario.nombreUsuario))
                inputFechaNacimiento.setText(usuario.fechaNacimiento.toString())

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

    private fun agregarListeners() {
        listenerBtnHome(btnMenuPrincipal)
        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)

        //ToDO: Funcionalidad botones
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }
}