package com.example.proyectosegundo.model.repository

import android.os.Bundle
import android.view.WindowInsetsAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundo.R
import com.example.proyectosegundo.controller.navigation.ApiService
import com.example.proyectosegundo.model.data.Usuario
import com.example.proyectosegundo.utils.RetrofitInstance
import com.example.proyectosegundo.utils.UsuarioRetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etUsername: EditText
    private lateinit var btnRegister: Button

    private val apiService = UsuarioRetrofitInstance.usuarioService

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
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etUsername = findViewById(R.id.etUsername)
        btnRegister = findViewById(R.id.btnRegister)
    }

    private fun registerUser() {
        val firstName = etFirstName.text.toString().trim()
        val lastName = etLastName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val username = etUsername.text.toString().trim()

        try {
            // Validar que los campos no estén vacíos
            validateFields(firstName, lastName, email, password)

            // Validar la seguridad de la contraseña
            validatePassword(password)

            // Verificar si el correo electrónico ya existe
            checkEmailExists(email) { exists ->
                if (exists) {
                    Toast.makeText(
                        this,
                        "El correo electrónico ya está en uso, intenta con otro",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    // Registrar al usuario

                    val user = Usuario(0, firstName, lastName, username, email, password)
                    registerUserInApi(user)
                }
            }
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun validateFields(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
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

    private fun handleApiError(exception: Exception) {
        Toast.makeText(
            this@RegisterActivity,
            "Error al conectar con el servidor: ${exception.message}",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkEmailExists(email: String, callback: (Boolean) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<Boolean> = apiService.checkEmail(email) // Llama a la API
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        callback(response.body() ?: false) // Si la respuesta es exitosa, se pasa el cuerpo
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error al verificar el correo electrónico",
                            Toast.LENGTH_LONG
                        ).show()
                        callback(false) // Si falla, devolver false
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error al conectar con el servidor: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    callback(false) // Si ocurre una excepción, devolver false
                }
            }
        }
    }

    // Registra un nuevo usuario
    private fun registerUserInApi(usuario: Usuario) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Response<Usuario> = apiService.registerUser(usuario) // Sin .execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterActivity, "Registro exitoso", Toast.LENGTH_LONG)
                            .show()
                        finish()  // Cierra la actividad después del registro exitoso
                    } else {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error al registrar el usuario: ${response.message()}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error al conectar con el servidor: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}