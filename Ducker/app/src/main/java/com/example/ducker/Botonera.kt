package com.example.ducker

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

open class Botonera: AppCompatActivity() {
    protected open var authKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()
        super.onCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        val intent = Intent(this, Feed::class.java)
        startActivity(intent.putExtra("authKey", authKey))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }

    protected fun listenerBtnHome(imageView: ImageView){
        imageView.setOnClickListener {
//            val intent : Intent = Intent(this, NuevoQuack::class.java)
//            startActivity(intent.putExtra("authKey", authKey))
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//            finish()
        }
    }

    protected fun listenerBtnBuscar(imageView: ImageView){
        imageView.setOnClickListener {
            val intent = Intent(this, Buscador::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }

    protected fun listenerBtnQuack(imageView: ImageView){
        imageView.setOnClickListener {
            val intent : Intent = Intent(this, NuevoQuack::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }

    protected fun listenerGuardado(imageView: ImageView){
        imageView.setOnClickListener {
//            val intent : Intent = Intent(this, NuevoQuack::class.java)
//            startActivity(intent.putExtra("authKey", authKey))
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
//            finish()
        }
    }

    protected fun listenerBtnPerfil(imageView: ImageView){
        imageView.setOnClickListener {
            val intent = Intent(this, PerfilUsuario::class.java)
            intent.putExtra("authKey", authKey)
            intent.putExtra("id", "0")
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            finish()
        }
    }
}