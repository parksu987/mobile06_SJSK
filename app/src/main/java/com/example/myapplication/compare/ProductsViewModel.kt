package com.example.myapplication.compare
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ProductViewModel : ViewModel() {
    // 제품 리스트를 MutableStateFlow를 사용하여 선언
    val products = mutableStateListOf<Product>()

    fun addProduct(product: Product) {
        // 중복 제품 추가 방지
        if (!products.contains(product)) {
            products.add(product)
        }
    }

    fun removeProduct(product: Product) {
        products.remove(product)
    }

}