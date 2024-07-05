package com.example.ptc1

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import modelo.ClaseConexion
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException

class Ulogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ulogin)

        // Configurar padding según barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtcorreologin = findViewById<EditText>(R.id.txtcorreologin)
        val txtcontralogin = findViewById<EditText>(R.id.txtContralogin)
        val btniniciarsesion = findViewById<Button>(R.id.btniniciarsesion)
        val btncontraolvidada = findViewById<Button>(R.id.btncontraolvidada)
        val imgvercontra = findViewById<ImageView>(R.id.idvercontra)

        btniniciarsesion.setOnClickListener {
            val correo = txtcorreologin.text.toString()
            val contrasena = txtcontralogin.text.toString()
            var hayerrores = false

            if(!correo.matches(Regex("[[a-zA-Z0-9._ - ]+@[a-z]+[.][a-z]+]"))){
                txtcorreologin.error = "Formato de correo no válido"
                hayerrores = true
            }

            else{
                txtcorreologin.error = null
            }

            if (contrasena.length <=7){
                txtcontralogin.error = "La contraseña debe tener un mínimo de 8 carácteres"
                hayerrores = true

            }

            else{
                txtcontralogin.error = null
            }


            // Ejecutar la lógica de inicio de sesión en un hilo de fondo
            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val connection = ClaseConexion().cadenaConexion()
                    val query = "SELECT * FROM Usuario WHERE correo_electronico = ? AND contraseña = ?"
                    val statement = connection?.prepareStatement(query)
                    statement?.setString(1, correo)
                    statement?.setString(2, contrasena)

                    val resultSet = statement?.executeQuery()

                    // Cambiar al hilo principal para actualizar la interfaz de usuario
                    withContext(Dispatchers.Main) {
                        if (resultSet?.next() == true) {
                            // Usuario autenticado correctamente
                            val tieneComite = checkForComite()
                            if (tieneComite) {
                                val intent = Intent(this@Ulogin, Pantalla_Principal::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(this@Ulogin, unirse_comite::class.java)
                                startActivity(intent)
                            }
                        } else {
                            Toast.makeText(this@Ulogin, "Usuario no encontrado, verifique las credenciales", Toast.LENGTH_LONG).show()
                        }
                    }
                } catch (e: SQLException) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@Ulogin, "Error de conexión: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

        btncontraolvidada.setOnClickListener {
            val intent = Intent(this, RecuperarContrasena::class.java)
            startActivity(intent)
        }

        imgvercontra.setOnClickListener {
            // Alternar visibilidad de la contraseña
            if (txtcontralogin.inputType == InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD) {
                txtcontralogin.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                txtcontralogin.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

    // Función para verificar la existencia de un comité en la base de datos
    private fun checkForComite(): Boolean {
        var connection: Connection? = null
        var preparedStatement: PreparedStatement? = null
        var resultSet: ResultSet? = null
        var hasComite = false

        try {
            connection = ClaseConexion().cadenaConexion()
            val query = "SELECT COUNT(*) AS count FROM usuario WHERE id_comite IS NOT NULL"
            preparedStatement = connection?.prepareStatement(query)
            resultSet = preparedStatement?.executeQuery()

            if (resultSet?.next() == true) {
                val count = resultSet.getInt("count")
                hasComite = count > 0
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        } finally {
            try {
                resultSet?.close()
                preparedStatement?.close()
                connection?.close()
            } catch (e: SQLException) {
                e.printStackTrace()
            }
        }

        return hasComite
    }
}
