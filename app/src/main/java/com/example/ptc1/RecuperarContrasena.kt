package com.example.ptc1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RecuperarContrasena : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recuperar_contrasena)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgregresarlogin = findViewById<ImageView>(R.id.imgregresar)
        val txtcorreocontraolvidada = findViewById<EditText>(R.id.txtcorreocontraolvidada)
        val btnsolicitarcorreo = findViewById<Button>(R.id.btnsolicitarcorreo)

        imgregresarlogin.setOnClickListener {
            val login = Intent (this, imgregresarlogin::class.java)
            startActivity(login)
        }

    }
}