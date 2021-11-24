package com.example.ducker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ducker.data.Quack
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    val dateFormat = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = dateFormat.format(Date())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}