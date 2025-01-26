package com.example.proyectosegundo.controller.navigation

import com.example.proyectosegundo.model.data.Usuario
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Endpoint para registrar un usuario
    @POST("usuarios/register")
    suspend fun registerUser(@Body usuario: Usuario): Call<Usuario>

    // Endpoint para verificar si el correo ya está registrado
    @GET("usuarios/check-email")
    suspend fun checkEmail(@Query("email") email: String): Call<Boolean>

    // Otros métodos relacionados con usuarios, si es necesario.
    @POST("usuarios/login")
    suspend fun loginUsuario(@Body usuario: Usuario): Response<Usuario>

}