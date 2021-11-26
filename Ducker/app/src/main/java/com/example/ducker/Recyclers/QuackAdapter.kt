package com.example.ducker.Recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.R
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.item_quack.view.*


class QuackAdapter(val listaQuacks: List<Quack>, val activity: Activity): RecyclerView.Adapter<QuackAdapter.QuackHolder>(){
    class QuackHolder(val view: View):  RecyclerView.ViewHolder(view){
        fun render(quack: Quack, activity: Activity){
            view.nombrePropio.text = quack.nombrePropio
            view.nombreUsuario.text = quack.nombreUsuario
            view.hora.text = quack.fechaHora
            view.texto.text = quack.texto
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuackHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuackHolder(layoutInflater.inflate(R.layout.item_quack, parent, false))
    }

    override fun onBindViewHolder(holder: QuackHolder, position: Int) {
        holder.render(listaQuacks[position], activity)
    }

    override fun getItemCount(): Int {
        return listaQuacks.size
    }
}