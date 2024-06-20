package com.example.myapplication.viewmodel
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import com.example.myapplication.compare.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductViewModel : ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    fun addProduct(product: Product) {
        if (!_products.value.contains(product)) {
            _products.value += listOf(product)
        }
    }

    fun removeProduct(product: Product) {
        _products.value = _products.value - listOf(product)
    }
}
