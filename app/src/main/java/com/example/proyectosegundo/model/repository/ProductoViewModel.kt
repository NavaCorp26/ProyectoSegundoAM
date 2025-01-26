package com.example.proyectosegundo.model.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectosegundo.model.data.Product
import kotlinx.coroutines.Dispatchers

class ProductoViewModel : ViewModel() {

    private val _productos = MutableLiveData<List<Product>>()
    val productos: LiveData<List<Product>> get() = _productos

    fun setProductos(productos: List<Product>) {
        _productos.value = productos
    }

    fun agregarProducto(producto: Product) {
        val updatedList = _productos.value?.toMutableList() ?: mutableListOf()
        updatedList.add(producto)
        _productos.value = updatedList
    }
}