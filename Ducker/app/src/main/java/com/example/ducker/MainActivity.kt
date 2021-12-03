package com.example.ducker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ducker.daos.Authentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private var authKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        leerAuthKey()
        siguienteActivity()
    }

    private fun leerAuthKey(){
        val preferences: SharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        authKey = preferences.getString("authKey", "").toString()
    }

    private fun siguienteActivity(){
        val context = this
        CoroutineScope(Dispatchers.IO).launch{
            val esValida = Authentication.validarToken(authKey)
            runOnUiThread {
                if(esValida){
                    val intent = Intent(context, Feed::class.java)
                    startActivity(intent.putExtra("authKey", authKey))
                    finish()
                }
                else{
                    val intent = Intent(context, Login::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

//    fun borrarKey(){
//        val preferences: SharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = preferences.edit()
//        editor.clear()
//        editor.apply()
//    }
}