package com.example.proyectosegundo.controller.navigation
import com.example.proyectosegundo.model.data.Usuario
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuarioService {
    @GET("usuarios")
    suspend fun getUsuarios(): Response<List<Usuario>>

    @GET("checkEmail/{email}")
    suspend fun checkEmail(@Path("email") email: String): Response<Boolean>

    @POST("register")
    suspend fun registerUser(@Body usuario: Usuario): Response<Usuario>

    @GET("usuarios/{userId}")
    suspend fun getUsuarioById(@Path("userId") userId: Long): Response<Usuario>

    @POST("usuarios")
    suspend fun createUsuario(@Body usuario: Usuario): Response<Usuario>

    @PUT("usuarios/{userId}")
    suspend fun updateUsuario(@Path("userId") userId: Long, @Body usuario: Usuario): Response<Usuario>

    @DELETE("usuarios/{userId}")
    suspend fun deleteUsuario(@Path("userId") userId: Long): Response<Void>
}