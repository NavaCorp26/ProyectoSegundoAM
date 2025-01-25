package com.example.proyectosegundo.model.repository

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectosegundo.R
import com.example.proyectosegundo.model.data.Usuario
import com.google.firebase.database.FirebaseDatabase

class UsuarioDetalleActivity : AppCompatActivity() {

    private lateinit var tvNombreCompleto: TextView
    private lateinit var tvNombreUsuario: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvBanco: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario_detalle)

        initComponent()
        mostrarDatos()

        btnDelete.setOnClickListener {
            deleteUsuario(intent.getStringExtra("id").toString())
        }
    }

    private fun initComponent() {
        tvNombreCompleto = findViewById(R.id.tvNombreCompleto)
        tvNombreUsuario = findViewById(R.id.tvNombreUsuario)
        tvEmail = findViewById(R.id.tvEmail)
        //tvBanco = findViewById(R.id.tvBanco)
        //btnUpdate = findViewById(R.id.btnUpdate)
        //btnDelete = findViewById(R.id.btnDelete)
    }

    private fun mostrarDatos() {
        tvNombreCompleto.text = "${intent.getStringExtra("nombre")} ${intent.getStringExtra("apellido")}"
        tvNombreUsuario.text = intent.getStringExtra("nombreUsuario")
        tvEmail.text = intent.getStringExtra("email")
        tvBanco.text = intent.getStringExtra("banco")
    }




    private fun deleteUsuario(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Usuarios").child(id)
        dbRef.removeValue().addOnSuccessListener {
            Toast.makeText(this, "Usuario eliminado", Toast.LENGTH_LONG).show()
            finish()
        }.addOnFailureListener { error ->
            Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
}