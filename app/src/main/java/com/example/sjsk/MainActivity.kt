package com.example.sjsk

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sjsk.firestore.ApiResponse
import com.example.sjsk.ui.theme.SJSKTheme
import com.example.sjsk.viewModel.SjskViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SJSKTheme {
                val mainViewModel: SjskViewModel = viewModel()
                MyAppScreen(mainViewModel)

            }
        }
    }
}


@Composable
fun MyAppScreen(viewModel: SjskViewModel) {
    // UI 구성, 예를 들면 목록 또는 데이터 카드를 표시
    val data by viewModel.data.collectAsState()

    data?.let {
        // Compose UI에서 데이터 사용
        DataDisplay(data = it)
    }
}

@Composable
fun DataDisplay(data: ApiResponse) {
    // 데이터를 보여주는 간단한 카드 Composable
    if (data.body?.items?.isNotEmpty() == true) {
        val item = data.body?.items?.first() // 첫 번째 아이템을 사용하거나, 필요에 따라 다른 로직 적용
        Text(text = "식품코드: ${item?.FOOD_CD}, 식품명: ${item?.FOOD_NM_KR}")
    } else {
        Text("No items available")
    }
}

