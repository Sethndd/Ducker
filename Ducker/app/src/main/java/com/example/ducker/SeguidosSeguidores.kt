package com.example.ducker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.daos.SeguidosDAO
import com.example.ducker.data.Usuario
import com.example.ducker.recyclers.PerfilAdapter
import kotlinx.android.synthetic.main.activity_seguidos_seguidores.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeguidosSeguidores : AppCompatActivity() {
    private var authKey = ""
    private var tipo = ""
    private var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
        tipo = bundle?.getString("tipo").toString()
        id = bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seguidos_seguidores)

        rvPerfiles.layoutManager = LinearLayoutManager(this)
        cargarPerfiles()
    }
    private fun cargarPerfiles(){
        CoroutineScope(Dispatchers.IO).launch {
            lateinit var usuarios : List<Usuario>
            when(tipo){
                "seguidos" -> usuarios = SeguidosDAO.obtenerSeguidosUsuario(authKey, id)
                "seguidores" -> usuarios = SeguidosDAO.obtenerSeguidoresUsuario(authKey, id)
            }

            runOnUiThread{
                rvPerfiles.adapter = PerfilAdapter(usuarios, authKey, this@SeguidosSeguidores)
            }
        }
    }
}