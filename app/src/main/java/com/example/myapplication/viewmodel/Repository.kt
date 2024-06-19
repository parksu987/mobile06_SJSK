package com.example.myapplication.viewmodel

import com.example.myapplication.firestore.ApiResponse
import com.example.myapplication.firestore.ApiService
import retrofit2.Call
import retrofit2.Response
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


class Repository(private val apiService: ApiService) {
    suspend fun fetchFoodData(pageNo: Int, foodName: String): Call<ApiResponse> {
        val encodedFoodName = encodeValue(foodName)
        return apiService.getData(pageNo = pageNo, foodName = encodedFoodName)
    }
}

fun encodeValue(value: String): String {
    return URLEncoder.encode(value, StandardCharsets.UTF_8.toString())
}