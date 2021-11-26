package com.example.ducker

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
    private var autKey = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_quack)
        val bundle = intent.extras
        autKey = bundle?.getString("authKey").toString()

        agregarListeners()
    }

    private fun agregarListeners(){
        btnPublicar.setOnClickListener {
            val textoQuack = txtQuack.text.toString()
            val quack = Quack(0, 0, textoQuack, 0,Date(), "activo", 0, "", "")
            CoroutineScope(Dispatchers.IO).launch {
                val statusResultado = QuackDAO.crearQuack(autKey, quack)
                runOnUiThread{
                    if (statusResultado == 201) {
                        Toast.makeText(this@NuevoQuack, "Quack Publicado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@NuevoQuack, "Hubo un error al publicar el Quack", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            finish()
        }

    }
}