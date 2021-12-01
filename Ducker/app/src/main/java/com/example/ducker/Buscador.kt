package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Buscador : AppCompatActivity() {
    private var authKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscador)
    }

    override fun onBackPressed() {
        val intent = Intent(this, Feed::class.java)
        startActivity(intent.putExtra("authKey", authKey))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}

