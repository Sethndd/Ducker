package com.example.ducker

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import kotlinx.android.synthetic.main.activity_nuevo_quack.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class NuevoQuack : AppCompatActivity() {
    private var authKey = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        authKey = bundle?.getString("authKey").toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_quack)

        agregarListeners()
    }

    private fun agregarListeners(){
        val sonidoCuack = MediaPlayer.create(this, R.raw.sonido_cuack)

        btnPublicar.setOnClickListener {
            val textoQuack = txtQuack.text.toString()
            val quack = Quack(0, 0, textoQuack, 0, Date(), "activo", 0, "", "")

            CoroutineScope(Dispatchers.IO).launch {
                val statusResultado = QuackDAO.crearQuack(authKey, quack)

                runOnUiThread{
                    if (statusResultado == 201) {
                        Toast.makeText(this@NuevoQuack, "Quack Publicado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@NuevoQuack, "Hubo un error al publicar el Quack", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            sonidoCuack.start()

            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Feed::class.java)
        startActivity(intent.putExtra("authKey", authKey))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}