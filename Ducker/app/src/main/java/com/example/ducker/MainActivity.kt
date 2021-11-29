package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.ducker.data.Quack

class MainActivity : AppCompatActivity() {
    private var authKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var abrirRegistrarQuack = findViewById<ImageView>(R.id.btnNuevoQuack)
        var abrirBuscador = findViewById<ImageView>(R.id.btnBuscador)

        abrirRegistrarQuack.setOnClickListener {
            val builder = AlertDialog.Builder(this@MainActivity)
            val view = layoutInflater.inflate(R.layout.activity_nuevo_quack, null)

            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
        }

        abrirBuscador.setOnClickListener {
            val intent = Intent(this, Buscador::class.java)
            startActivity(intent)
        }


        //ToDo Obtener de algun lado del dispositivo (SQLite??????)
//        authKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJjb3JyZW8iOiJzZXRoMjYxMDk5QGdtYWlsLmNvbSIsIm5vbWJyZVByb3BpbyI6IlNldGggTm_DqSBEw61heiBEw61heiIsIm5vbWJyZVVzdWFyaW8iOiJTZXRoIiwidGlwbyI6ImFscGhhIn0sImlhdCI6MTYzNzc5NDYxN30.UWcP6tNnR8jbZhWPB3QWS_p-nRCzJfA5VfpuuDSmKd8"

        if(authKey.equals("")){
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
        }

        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
    }

}