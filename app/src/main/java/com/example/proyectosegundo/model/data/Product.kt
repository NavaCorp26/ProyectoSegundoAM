package com.example.proyectosegundo.model.data

data class Product(
    val productoId: String,
    val nombre: String,
    val categoria: String,
    val precio: String,
    val stock: Int,
    val miniaturaUrl: String,
    val imagenes: List<String>
) {
    constructor() : this("", "", "", "", 0, "", emptyList())
}