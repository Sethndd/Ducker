package com.example.ducker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.ducker.daos.HashtagDAO
import com.example.ducker.daos.UsuarioDAO
import com.example.ducker.data.Hashtag
import com.example.ducker.data.Usuario
import kotlinx.android.synthetic.main.activity_buscador.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.AdapterView

import android.widget.AdapterView.OnItemClickListener
import com.example.ducker.recyclers.QuackAdapter


class Buscador : Botonera() {
    private var listaHashtags : List<Hashtag> = listOf()
    private var listaPersonas : List<Usuario> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador)

        agregarListeners()
        cargarDatos()

    }

    private fun agregarListeners() {
        listenerBtnHome(btnMenuPrincipal)
//        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)


        inputBuscador.setOnItemClickListener(OnItemClickListener { parent, view, position, rowId ->
            val selection = parent.getItemAtPosition(position)
            cargarQuacksBusqueda(selection)
        })
    }


    private fun agregarDropDown(){
        var listaResultados = listaHashtags + listaPersonas
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, listaResultados)
        inputBuscador.setAdapter(adapter)
    }

    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
             listaHashtags = HashtagDAO.obtenerHashtagsPopulares(authKey)
            listaPersonas = UsuarioDAO.obtener(authKey)
            runOnUiThread {
                agregarDropDown()
            }

        }
    }

    private fun cargarQuacksBusqueda(terminoBusqueda : Any){
        if (terminoBusqueda is Hashtag){
            CoroutineScope(Dispatchers.IO).launch {
                val listaQuacks = 
            }
            val adapter = QuackAdapter()
        }
        else if (terminoBusqueda is Usuario){

        }
    }
}

