package com.example.proyectosegundo.utils

import com.example.proyectosegundo.controller.navigation.UsuarioService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object UsuarioRetrofitInstance {
    private const val BASE_URL = "http://localhost:8081/api/"

    val usuarioService: UsuarioService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())  // Asegúrate de usar Gson o cualquier convertidor adecuado
            .build()
            .create(UsuarioService::class.java)  // Crea la instancia de UsuarioService
    }

}