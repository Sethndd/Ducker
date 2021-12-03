package com.example.ducker

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private var authKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences: SharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
        authKey = preferences.getString("authKey", "").toString()

        if(authKey.equals("")){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
        else{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            finish()
        }
    }

//    fun borrarKey(){
//        val preferences: SharedPreferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = preferences.edit()
//        editor.clear()
//        editor.apply()
//    }
}