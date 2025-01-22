package com.example.proyectosegundo.view.home.login

data class LoginResponse(
    val message: String,
    val token: String,
    val user: User
)

data class User(
    val id: Int,
    val nombre: String,
    val correo: String
)
