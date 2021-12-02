package com.example.ducker.recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.R
import com.example.ducker.data.Quack

class QuackAdapter(val listaQuacks: List<Quack>, val auth: String, val activity: Activity): RecyclerView.Adapter<QuackHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuackHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuackHolder(layoutInflater.inflate(R.layout.item_quack, parent, false))
    }

    override fun onBindViewHolder(holder: QuackHolder, position: Int) {
        holder.render(listaQuacks[position], auth, activity)
    }

    override fun getItemCount(): Int {
        return listaQuacks.size
    }
}