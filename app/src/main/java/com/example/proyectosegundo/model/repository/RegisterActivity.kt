package com.example.proyectosegundo.model.repository

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundo.R
import com.example.proyectosegundo.model.data.Usuario
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        initComponents()

        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun initComponents() {
        etFirstName = findViewById(R.id.etFirstName)
        etLastName = findViewById(R.id.etLastName)
        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun registerUser() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val username = etUsername.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        try {
            // Validar que los campos no estén vacíos
            validateFields(firstName, lastName, username, email, password)

            // Validar la seguridad de la contraseña
            validatePassword(password)

            // Validar si el nombre de usuario ya existe en la base de datos
            checkUsernameExists(username) { exists ->
                if (exists) {
                    Toast.makeText(
                        this,
                        "El nombre de usuario ya está en uso, intenta con otro",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Registrar al usuario
                    saveUserToDatabase(firstName, lastName, username, email, password)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun validateFields(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            throw Exception("Por favor, llena todos los campos")
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            throw Exception("El correo electrónico no tiene un formato válido")
        }
    }

    private fun validatePassword(password: String) {
        val passwordRegex = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\S+\$).{8,}\$".toRegex()

        if (!passwordRegex.matches(password)) {
            throw Exception(
                """
                La contraseña debe cumplir con los siguientes requisitos:
                - Al menos 8 caracteres
                - Incluir al menos un número
                - Incluir al menos una letra mayúscula
                - Incluir al menos un carácter especial (@#\$%^&+=!)
                - No debe contener espacios
                """.trimIndent()
            )
        }
    }

    private fun checkUsernameExists(username: String, callback: (Boolean) -> Unit) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Usuarios")
        dbRef.orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    callback(snapshot.exists())
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@RegisterActivity, "Error: ${error.message}", Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun saveUserToDatabase(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ) {
        val userId = FirebaseDatabase.getInstance().reference.push().key ?: ""
        val usuario = Usuario(userId, firstName, lastName, username, email, password)

        val dbRef = FirebaseDatabase.getInstance().getReference("Usuarios")
        dbRef.child(userId).setValue(usuario)
            .addOnSuccessListener {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_LONG).show()
                finish()
            }
            .addOnFailureListener { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
            }
    }
}
