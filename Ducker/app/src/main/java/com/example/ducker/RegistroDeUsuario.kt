package com.example.ducker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ducker.daos.Authentication
import com.example.ducker.data.Usuario
import kotlinx.android.synthetic.main.activity_registro_de_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class RegistroDeUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_de_usuario)

        agregarListeners()
    }

    private fun agregarListeners() {
        btnCrearCuenta.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val usuario = inputUsuario.text.toString()
            val correo = inputCorreo.text.toString()
            val fechaNacimiento = inputFechaNacimiento.text.toString()
            val contrasena = inputPassword.text.toString()
            val contrasena2 = inputConfirmarPassword.text.toString()

            if(contrasena == contrasena2){
                //ToDo Recuperar fecha de la GUI
                val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                val dateString: String = simpleDateFormat.format(Date())

                val usuario = Usuario(0, correo, contrasena, nombre, usuario, dateString)
                val context: Context = this

                CoroutineScope(Dispatchers.IO).launch {
                    val codigo = Authentication.registrar(usuario)

                    runOnUiThread {
                        if((codigo > 199) and (codigo < 300)){
                            val intent = Intent(context, Login::class.java)
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(context, "Correo electrónico o nombre de usuario ya registrado anteriormente", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            else{
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }
}