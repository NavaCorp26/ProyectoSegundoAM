package com.example.proyectosegundo.view.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectosegundo.R
import com.example.proyectosegundo.model.data.Usuario
import com.example.proyectosegundo.model.repository.ProductsActivity
import com.example.proyectosegundo.model.repository.RegisterActivity
import com.example.proyectosegundo.model.repository.TiendaActivity
import com.example.proyectosegundo.model.repository.UploadProductActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
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

        btnProduct.setOnClickListener {
            val intent = Intent(this, UploadProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun autenticarUsuario(correo: String, contrasena: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Usuarios")
        dbRef.orderByChild("userEmail").equalTo(correo).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Se encontró el usuario con el correo
                    for (userSnapshot in snapshot.children) {
                        val storedPassword = userSnapshot.child("userPass").getValue(String::class.java)

                        // Comparar la contraseña
                        if (storedPassword == contrasena) {
                            // Autenticación exitosa, proceder al inicio
                            val intent = Intent(this@MainActivity, TiendaActivity::class.java)
                            startActivity(intent)
                            finish()  // Cerrar la actividad de login
                        } else {
                            Toast.makeText(this@MainActivity, "Contraseña incorrecta", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}