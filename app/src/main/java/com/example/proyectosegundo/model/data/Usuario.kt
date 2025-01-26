package com.example.proyectosegundo.model.data

data class Usuario(
    val userId: Long,
    val userFirstName: String,
    val userLastName: String,
    val username: String,
    val userEmail: String,
    val userPass: String
) {

    constructor() : this(
        0,
        "",
        "",
        "",
        "",
        "")
}

