package com.example.proyectosegundo.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundo.R
import com.example.proyectosegundo.controller.navigation.ApiService
import com.example.proyectosegundo.model.data.Usuario
import com.example.proyectosegundo.model.repository.RegisterActivity
import com.example.proyectosegundo.model.repository.TiendaActivity
import com.example.proyectosegundo.model.repository.UploadProductActivity
import com.example.proyectosegundo.utils.RetrofitInstance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val apiService: ApiService = RetrofitInstance.usuarioService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnRegistrar = findViewById<Button>(R.id.btnResgitro)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnProduct = findViewById<Button>(R.id.btnProduct)

        // Configurar el listener para el botón de login
        btnLogin.setOnClickListener {
            val correo = findViewById<EditText>(R.id.etCorreo).text.toString().trim()
            val contrasena = findViewById<EditText>(R.id.etContrasena).text.toString().trim()

            if (correo.isEmpty() || contrasena.isEmpty()) {
                Toast.makeText(this, "Por favor ingrese correo y contraseña", Toast.LENGTH_LONG)
                    .show()
            } else {
                autenticarUsuario(correo, contrasena)
            }
        }

        // Configurar el listener para el botón de registro
        btnRegistrar.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // Configurar el listener para el botón de productos
        btnProduct.setOnClickListener {
            val intent = Intent(this, UploadProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun autenticarUsuario(correo: String, contrasena: String) {
        val usuario = Usuario(0, "", "", correo, correo, contrasena)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.loginUsuario(usuario)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        // Autenticación exitosa, proceder al inicio
                        val intent = Intent(this@MainActivity, TiendaActivity::class.java)
                        startActivity(intent)
                        finish()  // Cerrar la actividad de login
                    } else {
                        Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error al autenticar: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}