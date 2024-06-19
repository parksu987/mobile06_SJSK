package com.example.myapplication.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log
import com.example.myapplication.firestore.ApiResponse
import com.example.myapplication.firestore.RetrofitClient


//Compose에서 API 데이터를 요청하고 화면에 표시
class SjskViewModel : ViewModel() {
    private val _data = MutableStateFlow<ApiResponse?>(null)
    val data = _data.asStateFlow()

//    init {
//        loadData()  // 예시로 "국밥_돼지머리"를 로드하는 경우
//    }

    private fun loadData(pageNo: Int, foodName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitClient.instance.getData(pageNo = pageNo , foodName = foodName).execute()
                if (response.isSuccessful) {
                    _data.value = response.body()
                    Log.d("API_SUCCESS", "Response successful: ${response.body()} \nresponse: ${response}")
                } else {
                    // Handle API error response
                    _data.value = null
                    Log.e("API_ERROR", "Response not successful: ${response.errorBody()?.string()} \n" +
                            "response: ${response}")
                }
            } catch (e: Exception) {
                // Handle network error or other exceptions
                _data.value = null
                Log.e("API_EXCEPTION", "Error during API call: ${e.message}", e)
            }
        }
    }
}


