package com.example.ptc1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ptc1.R.id.btnaceptarcontrasenarestablecida

class Contrasena_Reestablecida : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contrasena_reestablecida)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnaceptarcontrasenarestablecida = findViewById<Button>(btnaceptarcontrasenarestablecida)

        btnaceptarcontrasenarestablecida.setOnClickListener {
            val pantallalogin = Intent (this, Pantalla_Principal::class.java)
            startActivity(pantallalogin)
        }

    }
}