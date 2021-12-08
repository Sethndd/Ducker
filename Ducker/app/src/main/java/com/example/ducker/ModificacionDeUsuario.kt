package com.example.ducker

import android.app.DatePickerDialog
import android.app.Dialog
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.ducker.daos.PerfilDAO
import com.example.ducker.daos.UsuarioDAO
import com.example.ducker.util.CyrclePicasso
import com.example.ducker.util.Rutas
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_modificacion_de_usuario.*
import kotlinx.android.synthetic.main.activity_modificacion_de_usuario.inputFechaNacimiento
import kotlinx.android.synthetic.main.activity_modificacion_de_usuario.inputNombre
import kotlinx.android.synthetic.main.activity_modificacion_de_usuario.inputUsuario
import kotlinx.android.synthetic.main.activity_registro_de_usuario.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ModificacionDeUsuario : Botonera() {
    var idUsuario = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val bundle = intent.extras
        idUsuario = bundle?.getString("id").toString().toInt()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modificacion_de_usuario)

        cargarPerfil()
        agregarListeners()
    }

    private fun cargarPerfil() {
        CoroutineScope(Dispatchers.IO).launch{
            val usuario = UsuarioDAO.obtener(authKey, idUsuario)
            val perfil = PerfilDAO.obtener(authKey, idUsuario)

            runOnUiThread {
                inputNombre.setText(usuario.nombrePropio)
                inputUsuario.setText("@".plus(usuario.nombreUsuario))
                inputFechaNacimiento.setText(usuario.fechaNacimiento)

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.imagenRuta))
                    .transform(CyrclePicasso())
                    .into(btnFotoPerfil)

                Picasso.get()
                    .load(Rutas.IMAGENES.plus(perfil.bannerRuta))
                    .fit()
                    .centerCrop()
                    .into(btnPortada)
            }
        }
    }

    private fun agregarListeners() {
        listenerBtnHome(btnMenuPrincipal)
        listenerBtnBuscar(btnBuscador)
        listenerBtnQuack(btnNuevoQuack)
        listenerGuardado(btnGuardados)
        listenerBtnPerfil(btnPerfil)

        //ToDO: Funcionalidad botones
        inputFechaNacimiento.setOnClickListener{
            val dialogFecha = RegistroDeUsuario.DatePickerFragment { ano, mes, dia ->
                mostrarResultado(
                    ano,
                    mes,
                    dia
                )
            }
            dialogFecha.show(supportFragmentManager, "DatePicker")
        }
    }

    override fun onBackPressed() {
        finish()
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun mostrarResultado(ano: Int, mes: Int, dia: Int) {
        inputFechaNacimiento?.setText("$dia/$mes/$ano")
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
}