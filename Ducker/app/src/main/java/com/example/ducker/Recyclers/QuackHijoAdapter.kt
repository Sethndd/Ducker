package com.example.ducker.Recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.R
import com.example.ducker.data.Quack

class QuackHijoAdapter(val listaQuacks: List<Quack>, val auth: String, val activity: Activity): RecyclerView.Adapter<QuackHijoHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuackHijoHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuackHijoHolder(layoutInflater.inflate(R.layout.item_quack_hijo, parent, false))
    }

    override fun onBindViewHolder(holder: QuackHijoHolder, position: Int) {
        holder.render(listaQuacks[position], auth, activity)
    }

    override fun getItemCount(): Int {
        return listaQuacks.size
    }
}