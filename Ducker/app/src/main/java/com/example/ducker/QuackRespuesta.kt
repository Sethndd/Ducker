package com.example.ducker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.QuackDAO
import com.example.ducker.data.Quack
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_nuevo_quack.*
import kotlinx.android.synthetic.main.activity_quack_detalles.*
import kotlinx.android.synthetic.main.item_quack.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class QuackRespuesta : AppCompatActivity() {
    private var authKey = ""
    private var idQuackPadre: Int = 0

        override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        idQuackPadre= bundle?.getString("id").toString().toInt()
        authKey = bundle?.getString("authKey").toString()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quack_respuesta)

        cargarQuackPadre()
        agregarListeners()
    }

    private fun agregarListeners() {
        btnPublicar.setOnClickListener {
            val textoQuack = txtQuack.text.toString()
            val quack = Quack(0, 0, textoQuack, idQuackPadre, Date(), "activo", 0, "", "")

            CoroutineScope(Dispatchers.IO).launch {
                val statusResultado = QuackDAO.crearQuack(authKey, quack)

                runOnUiThread{
                    if (statusResultado == 201) {
                        Toast.makeText(this@QuackRespuesta, "Quack Publicado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@QuackRespuesta, "Hubo un error al publicar el Quack", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            val intent = Intent(this, Feed::class.java)
            startActivity(intent.putExtra("authKey", authKey))
            finish()
        }
    }

    private fun cargarQuackPadre() {
        CoroutineScope(Dispatchers.IO).launch{
            val quack: Quack = QuackDAO.obtenerQuackPorId(authKey, idQuackPadre)
            val perfil = PerfilDAO.obtener(authKey, quack.idUsuario)

            runOnUiThread {
                var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

                if (simpleDateFormat.format(Date()) == simpleDateFormat.format(quack.fechaHora)) {
                    simpleDateFormat = SimpleDateFormat("HH:mm")
                }

                nombrePropio.text = quack.nombrePropio
                nombreUsuario.text = "@".plus(quack.nombreUsuario)
                hora.text = simpleDateFormat.format(quack.fechaHora)
                texto.text = quack.texto

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(fotoPerfil)
            }
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this, Feed::class.java)
        startActivity(intent.putExtra("authKey", authKey))
        finish()
    }
}