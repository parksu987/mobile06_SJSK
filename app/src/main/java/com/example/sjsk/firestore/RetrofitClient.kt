package com.example.sjsk.firestore

import ApiService
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


// API 요청 수행
object RetrofitClient {
    private const val BASE_URL = "https://apis.data.go.kr/1471000/FoodNtrCpntDbInfo/" // API의 기본 URL

    private val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // Gson 인스턴스에 Lenient 모드를 활성화
    private val gson = GsonBuilder()
        .setLenient()
        .create()

    val instance: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) ////
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}