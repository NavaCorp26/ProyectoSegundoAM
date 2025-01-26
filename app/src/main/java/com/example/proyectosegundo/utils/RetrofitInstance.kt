package com.example.proyectosegundo.utils

import com.example.proyectosegundo.controller.navigation.ApiService
import com.example.proyectosegundo.controller.navigation.ProductService
import com.example.proyectosegundo.controller.navigation.UsuarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "http://localhost:8081/api/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)  // URL base de tu API
            .addConverterFactory(GsonConverterFactory.create())  // Asegúrate de usar Gson para la conversión
            .build()
    }

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }

    val usuarioService: ApiService by lazy {
        getRetrofitInstance().create(ApiService::class.java)  // Crear la instancia de ApiService
    }

    val productService: ProductService by lazy {
        getRetrofitInstance().create(ProductService::class.java)  // Crear la instancia de ProductService
    }
}