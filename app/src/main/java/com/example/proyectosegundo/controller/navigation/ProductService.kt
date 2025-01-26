package com.example.proyectosegundo.controller.navigation

import com.example.proyectosegundo.model.data.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ProductService {
    @GET("productos")
    suspend fun getProductos(): Response<List<Product>>

    @GET("productos/{id}")
    suspend fun getProductoById(@Path("id") productoId: Long): Response<Product>

    @POST("productos")
    suspend fun createProducto(@Body product: Product): Response<Product>

    @PUT("productos/{productoId}")
    suspend fun updateProducto(@Path("productoId") productoId: Long, @Body product: Product): Response<Product>

    @DELETE("productos/{productoId}")
    suspend fun deleteProducto(@Path("productoId") productoId: Long): Response<Void>
}
