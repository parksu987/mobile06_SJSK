package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.ku.MainScreen
import com.example.myapplication.compare.MultiNutrientBarChart
import com.example.myapplication.compare.ProductList
import com.example.myapplication.viewmodel.ProductViewModel
//import com.example.myapplication.notification.FetchAndNotifyWorker
//import com.example.myapplication.notification.utils.scheduleDailyUpdate
import com.example.myapplication.ui.theme.MyApplicationTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Initialize sample product data

//                    val products = rememberSaveable {
//                        listOf(
//                            Product(
//                                name = "강냉이/팝콘_33한체다치즈팝콘",
//                                energy = 478.0,
//                                carbohydrates = 66.2,
//                                sugars = 11.9,
//                                fat = 19.5,
//                                protein = 9.34,
//                                sodium = 594.0,
//                                saturatedFat = 11.6,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_CGV시그니처스위트팝콘",
//                                energy = 529.0,
//                                carbohydrates = 64.29,
//                                sugars = 7.14,
//                                fat = 28.57,
//                                protein = 4.29,
//                                sodium = 221.0,
//                                saturatedFat = 7.14,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_CGV시그니처카라멜&치즈팝콘",
//                                energy = 479.0,
//                                carbohydrates = 68.42,
//                                sugars = 28.42,
//                                fat = 20.0,
//                                protein = 6.32,
//                                sodium = 516.0,
//                                saturatedFat = 8.42,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_dardaby커널스슈퍼믹스팝콘",
//                                energy = 473.0,
//                                carbohydrates = 72.73,
//                                sugars = 16.36,
//                                fat = 20.0,
//                                protein = 4.0,
//                                sodium = 182.0,
//                                saturatedFat = 9.09,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_dardaby커널스씨네마팝콘",
//                                energy = 559.0,
//                                carbohydrates = 60.0,
//                                sugars = 0.0,
//                                fat = 32.73,
//                                protein = 6.36,
//                                sodium = 245.0,
//                                saturatedFat = 7.27,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_dardaby커널스씨네마팝콘카라멜맛",
//                                energy = 500.0,
//                                carbohydrates = 71.07,
//                                sugars = 0.0,
//                                fat = 21.79,
//                                protein = 5.0,
//                                sodium = 188.0,
//                                saturatedFat = 6.07,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_dardaby커널스치즈케이크맛팝콘",
//                                energy = 540.0,
//                                carbohydrates = 60.0,
//                                sugars = 40.0,  // Assumed value, please verify
//                                fat = 32.0,
//                                protein = 4.0,
//                                sodium = 440.0,
//                                saturatedFat = 12.0,
//                                cholesterol = 0.0
//                            ),
//                            Product(
//                                name = "강냉이/팝콘_dardaby커널스카라멜&크림치즈팝콘",
//                                energy = 467.0,
//                                carbohydrates = 60.0,
//                                sugars = 28.89,
//                                fat = 24.44,
//                                protein = 2.67,
//                                sodium = 493.0,
//                                saturatedFat = 12.0,
//                                cholesterol = 0.8
//                            )
//                        )
//                    }

                    //setupPeriodicWork(this)

                    val navController = rememberNavController()
                    MainScreen(navController)
//                    val p = Person("sj", "1234", "sj", 24, 153.3, 41.1, 2000, 100.0, 50.0, 40.0, mapOf())

                }
            }
        }
    }
}


//private fun setupPeriodicWork(context: Context) {
//    // 테스트를 위해 10초 후에 작업을 실행하도록 설정
//    val initialDelay = 17L // 10초 후 실행
//
////    val periodicWorkRequest = PeriodicWorkRequestBuilder<FetchAndNotifyWorker>(1, TimeUnit.DAYS)
//        .setInitialDelay(initialDelay, TimeUnit.SECONDS)
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        "daily_notification_work",
//        ExistingPeriodicWorkPolicy.KEEP,
//        periodicWorkRequest
//    )
//}

//private fun setupPeriodicWork(context: Context) {
//    // 지연 시간 계산: 예를 들어, 매일 오전 10시에 작업을 실행하고자 할 경우
//    val targetHour = 10
//    val currentDateTime = LocalDateTime.now()
//    val targetDateTime = currentDateTime.withHour(targetHour).withMinute(0).withSecond(0).withNano(0)
//    val initialDelay = if (currentDateTime <= targetDateTime) {
//        Duration.between(currentDateTime, targetDateTime).toMinutes()
//    } else {
//        Duration.between(currentDateTime, targetDateTime.plusDays(1)).toMinutes()
//    }
//
//    val periodicWorkRequest = PeriodicWorkRequestBuilder<FetchAndNotifyWorker>(1, TimeUnit.DAYS)
//        .setInitialDelay(initialDelay, TimeUnit.MINUTES)
//        .build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//        "daily_notification_work",
//        ExistingPeriodicWorkPolicy.KEEP,
//        periodicWorkRequest
//    )
//}

//@Composable
//fun MainContent(viewmodel: ProductViewModel = viewModel()) {
//    Column(modifier = Modifier.padding(16.dp)) {
//        // Example of toggling views
//        var showDetails by remember { mutableStateOf(true) }
//        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//            Button(onClick = { showDetails = true }) {
//                Text("제품 자세히 보기")
//            }
//            Button(onClick = { showDetails = false }) {
//                Text("성분별 순위 보기")
//            }
//        }
//
//        if (showDetails) {
//            ProductList(products = viewmodel.products, viewModel = viewmodel)
//        } else {
//
//            MultiNutrientBarChart(products = viewmodel.products, viewModel = viewmodel)
//        }
//    }
//}