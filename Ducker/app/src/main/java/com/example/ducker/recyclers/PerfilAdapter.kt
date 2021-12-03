package com.example.ducker.recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.R
import com.example.ducker.data.Usuario

class PerfilAdapter(val listaUsuarios: List<Usuario>, val auth: String, val activity: Activity): RecyclerView.Adapter<PerfilHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PerfilHolder(layoutInflater.inflate(R.layout.item_perfil, parent, false))
    }

    override fun onBindViewHolder(holder: PerfilHolder, position: Int) {
        holder.render(listaUsuarios[position], auth, activity)
    }

    override fun getItemCount(): Int {
        return listaUsuarios.size
    }
}