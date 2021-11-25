package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val abrirMenuPrincipal = findViewById<Button>(R.id.btnIniciarSesion)
        val abrirCrearCuenta = findViewById<Button>(R.id.btnCrearCuenta)

        abrirMenuPrincipal.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        abrirCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistroDeUsuario::class.java)
            startActivity(intent)
        }
    }
}