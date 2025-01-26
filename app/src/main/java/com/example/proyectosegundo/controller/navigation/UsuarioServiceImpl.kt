package com.example.proyectosegundo.controller.navigation

import com.example.proyectosegundo.model.data.Usuario
import com.example.proyectosegundo.utils.RetrofitInstance
import com.example.proyectosegundo.view.login.UsuarioRetrofitInstance

class UsuarioServiceImpl {
    private val usuarioService = UsuarioRetrofitInstance.usuarioService

    suspend fun getAllUsuarios(): List<Usuario>? {
        val response = usuarioService.getUsuarios()
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun getUsuarioById(userId: Long): Usuario? {
        val response = usuarioService.getUsuarioById(userId)
        return if (response.isSuccessful) response.body() else null
    }

    suspend fun createUsuario(usuario: Usuario): Usuario? {
        val response = usuarioService.createUsuario(usuario)
        return if (response.isSuccessful) response.body() else null
    }
}