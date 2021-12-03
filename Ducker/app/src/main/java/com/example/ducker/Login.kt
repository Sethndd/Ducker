package com.example.ducker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ducker.API.bodyClases.Login
import com.example.ducker.daos.Authentication
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        agregarListeners()
    }

    private fun agregarListeners() {
        btnIniciarSesion.setOnClickListener {
            val usuario = inputUsuario.text
            val contrasena = inputPassword.text

            val login = Login(usuario.toString(), contrasena.toString())
            val context: Context = this

            CoroutineScope(Dispatchers.IO).launch {
                val authKey = Authentication.iniciarSesion(login)
                runOnUiThread{
                    if(authKey == ""){
                        Toast.makeText(context, "Usuario o contraseña incorrecta. Intente nuevamente", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        val preferences: SharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = preferences.edit()
                        editor.putString("authKey", authKey)
                        editor.apply()

                        val intent = Intent(context, Feed::class.java)
                        startActivity(intent.putExtra("authKey", authKey))
                        finish()
                    }
                }
            }
        }

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegistroDeUsuario::class.java)
            startActivity(intent)
            finish()
        }
    }
}