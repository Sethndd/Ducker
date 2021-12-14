package com.example.ducker

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.ducker.daos.Authentication
import com.example.ducker.data.Usuario
import kotlinx.android.synthetic.main.activity_registro_de_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.consumesAll
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class RegistroDeUsuario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_de_usuario)

        agregarListeners()
    }

    private fun agregarListeners() {
        val sonidoCuack = MediaPlayer.create(this, R.raw.sonido_cuack)

        btnCrearCuenta.setOnClickListener {
            val nombre = inputNombre.text.toString()
            val usuario = inputUsuario.text.toString()
            val correo = inputCorreo.text.toString()
            val fechaNacimiento = inputFechaNacimiento.text.toString()
            val contrasena = inputPassword.text.toString()
            val contrasena2 = inputConfirmarPassword.text.toString()

//            val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy")
//            val dateString: String = simpleDateFormat.format(fechaNacimiento)
//
//            println(dateString)
            if(contrasena == contrasena2){

                val usuario = Usuario(0, correo, contrasena, nombre, usuario, fechaNacimiento)
                val context: Context = this

                CoroutineScope(Dispatchers.IO).launch {
                    val codigo = Authentication.registrar(usuario)

                    runOnUiThread {
                        if((codigo > 199) and (codigo < 300)){
                            sonidoCuack.start()

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

        inputFechaNacimiento.setOnClickListener{
            val dialogFecha = DatePickerFragment{ano, mes, dia -> mostrarResultado(ano, mes, dia) }
            dialogFecha.show(supportFragmentManager, "DatePicker")
        }
    }

    private fun mostrarResultado(ano: Int, mes: Int, dia: Int) {
        inputFechaNacimiento?.setText("$ano-$mes-$dia")
    }

    class DatePickerFragment (val listener: (year:Int, month:Int, day:Int) -> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val calendario = Calendar.getInstance()
            var ano = calendario.get(Calendar.YEAR)
            var mes = calendario.get(Calendar.MONTH)
            var dia = calendario.get(Calendar.DAY_OF_MONTH)

            val picker = DatePickerDialog(requireActivity(), R.style.DatePickerTheme, this, ano, mes, dia)
            picker.datePicker.maxDate = calendario.timeInMillis
            return picker
        }

        override fun onDateSet(view: DatePicker?, ano: Int, mes: Int, dia: Int) {
            listener(ano, mes+1, dia)
        }

    }

    override fun onBackPressed() {
        val intent = Intent(this, Login::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        finish()
    }
}