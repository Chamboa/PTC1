package com.example.ptc1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import modelo.ClaseConexion
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class Pantalla_De_Carga : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_de_carga) // Usa el layout pantalla_de_carga

        enableEdgeToEdge()

        // Handler para redirigir a la pantalla de inicio de sesión después de 300 milisegundos
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, Ulogin::class.java)
            startActivity(intent)
            finish() // Termina la actividad actual para que no se pueda volver atrás
        }, 300)
    }
}
