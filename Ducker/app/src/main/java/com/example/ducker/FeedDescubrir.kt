package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_feed.*

class FeedDescubrir : AppCompatActivity() {
    private lateinit var authKey : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed_descubrir)

        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()

        agregarListeners()
    }

    fun agregarListeners(){
        btnSiguiendo.setOnClickListener{
            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.left_out, R.anim.left_in)
            finish()
        }

        btnBuscador.setOnClickListener{
            val intent = Intent(this, Buscador::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.left_out, R.anim.left_in)
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Feed::class.java)
        startActivity(intent.putExtra("authKey", authKey))
        overridePendingTransition(R.anim.left_out, R.anim.left_in)
        finish()
    }
}