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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import com.example.ducker.recyclers.QuackAdapter


class Buscador : Botonera() {
    private var listaHashtags : List<Hashtag> = listOf()
    private var listaPersonas : List<Usuario> = listOf()
    private var listaQuacks : ArrayList<Quack> = arrayListOf()
    private var listaResultados : List<Any> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador)
        recyclerView.layoutManager = LinearLayoutManager(this)

        agregarListeners()
        cargarDatos()

    }

    private fun agregarListeners() {
        listenerBtnHome(btnMenuPrincipal)
//        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)

        btnBuscar.setOnClickListener {
            limpiarLista()
            if (inputBuscador.text.toString() != "") {
                cargarQuacksBusqueda(inputBuscador.text.toString())
            }

        }

        inputBuscador.setOnItemClickListener(OnItemClickListener { parent, view, position, rowId ->
            val selection = parent.getItemAtPosition(position)
            limpiarLista()
            cargarQuacksBusqueda(selection)
        })
    }

    private fun limpiarLista(){
        listaQuacks = arrayListOf()
        recyclerView.adapter = QuackAdapter(listaQuacks, authKey, this, R.layout.item_quack)
    }


    private fun agregarDropDown(){
        listaResultados = listaHashtags + listaPersonas
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
        val activity = this
        when (terminoBusqueda) {
            is Hashtag -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val quacks = QuackDAO.obtenerQuacksPorHashtag(authKey, terminoBusqueda.hashtag)
                    for (quack in quacks) {
                        listaQuacks.add(quack)
                    }
                    runOnUiThread{
                        val adapter = QuackAdapter(listaQuacks, authKey, activity, R.layout.item_quack)
                        recyclerView.adapter = adapter
                    }
                }

            }
            is Usuario -> {
                CoroutineScope(Dispatchers.IO).launch {
                    val quacks = QuackDAO.obtenerQuacksPorUsuario(authKey, terminoBusqueda.idUsuario)
                    for (quack in quacks) {
                        listaQuacks.add(quack)
                    }
                    runOnUiThread{
                        val adapter = QuackAdapter(listaQuacks, authKey, activity, R.layout.item_quack)
                        recyclerView.adapter = adapter
                    }
                }
            }
            else -> {
                for (elemento in listaResultados) {
                    println(elemento.toString())
                    if(elemento.toString().contains(terminoBusqueda as String)) {
                        cargarQuacksBusqueda(elemento)
                    }
                }
            }
        }

    }
}

