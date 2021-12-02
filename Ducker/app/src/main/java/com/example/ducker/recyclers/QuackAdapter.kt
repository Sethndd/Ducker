package com.example.ducker.recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.R.layout.item_quack
import com.example.ducker.data.Quack

class QuackAdapter(val listaQuacks: List<Quack>, val auth: String, val activity: Activity, var layout: Int): RecyclerView.Adapter<QuackHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuackHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        if(layout == item_quack){
            return QuackHolder(layoutInflater.inflate(layout, parent, false))
        }

        return QuackHilo(layoutInflater.inflate(layout, parent, false))
    }

    override fun onBindViewHolder(holder: QuackHolder, position: Int) {
        holder.render(listaQuacks[position], auth, activity)
    }

    override fun getItemCount(): Int {
        return listaQuacks.size
    }
}