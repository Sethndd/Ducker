package com.example.ducker.Recyclers

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ducker.R
import com.example.ducker.data.Quack

class QuackPadreAdapter(val listaQuacks: List<Quack>, val auth: String, val activity: Activity): RecyclerView.Adapter<QuackPadreHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuackPadreHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return QuackPadreHolder(layoutInflater.inflate(R.layout.item_quack_padre, parent, false))
    }

    override fun onBindViewHolder(holder: QuackPadreHolder, position: Int) {
        holder.render(listaQuacks[position], auth, activity)
    }

    override fun getItemCount(): Int {
        return listaQuacks.size
    }
}