package com.example.proyectosegundo.controller.navigation

import com.example.proyectosegundo.model.data.Product
import com.example.proyectosegundo.utils.RetrofitInstance

class ProductServiceImpl {
    private val productService: ProductService = RetrofitInstance.productService

    suspend fun getAllProductos(): List<Product>? {
        val response = productService.getProductos()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getProductoById(productoId: Long): Product? {
        val response = productService.getProductoById(productoId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createProducto(product: Product): Product? {
        val response = productService.createProducto(product)
        return if (response.isSuccessful) response.body() else null
    }
}
