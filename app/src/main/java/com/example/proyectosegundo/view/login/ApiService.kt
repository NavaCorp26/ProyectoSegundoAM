package com.example.proyectosegundo.view.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/api/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
}