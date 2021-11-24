package com.example.ducker.data

import java.text.SimpleDateFormat

data class Quack (val idQuack:Int,
                  val idUsuario:Int,
                  val texto:String,
                  val quackPadre:Int,
                  val fechaHora:String,
                  val estado:String)