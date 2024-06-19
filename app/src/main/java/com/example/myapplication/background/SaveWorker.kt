package com.example.myapplication.background

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.roomDB.Eating
import com.example.myapplication.roomDB.EatingDatabase
import com.example.myapplication.viewmodel.EatingRepository
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.LoginRepository
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.PersonRepository
import com.example.myapplication.viewmodel.PersonViewModel
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate

@HiltWorker
class SaveWorker @AssistedInject constructor(
    @Assisted ctx:Context,
    @Assisted params: WorkerParameters,
    private var personViewModel: PersonViewModel,
    private var eatingViewModel: EatingViewModel
) : CoroutineWorker(ctx, params) {
    override suspend fun doWork(): Result {
        return try {
            var eating = eatingViewModel.todayEating

            var todayCarbohydrate = .0
            var todayProtein = .0
            var todayFat = .0

            for(e in eating.value){
                todayCarbohydrate += (e.carbohydrate * (e.gram/100))
                todayProtein += (e.protein * (e.gram/100))
                todayFat += (e.fat * (e.gram/100))
            }

            if(todayCarbohydrate == .0 && todayProtein == .0 && todayFat == .0){  //오늘 사용자가 todayEating을 쓰지 않았을 경우
                return Result.success()
            }

            val today = LocalDate.now().minusDays(1).toString()
            personViewModel.updatePersonIntake(mapOf(today to Nutrient(todayCarbohydrate, todayProtein, todayFat)))

            eatingViewModel.deleteAllEating()

            Log.d("todayEating", "skf")

            Result.success()
        } catch (e:Exception){
            Result.failure()
        }
    }
}
