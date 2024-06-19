package com.example.myapplication.background

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.LoginViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Provider

//@HiltWorker
class SaveWorker( //@AssistedInject constructor(
     ctx:Context,
     params: WorkerParameters,
    //private var loginViewModel: LoginViewModel,
    //private var eatingViewModel: EatingViewModel
) : CoroutineWorker(ctx, params) {

    override suspend fun doWork(): Result {

        Log.d("workManager", "doWork")

        return try {
            val carbohydrate = inputData.getString("carbohydrate")
            val protein = inputData.getString("protein")
            val fat = inputData.getString("fat")

            Log.d("workManager", "carbohydrate: $carbohydrate, protein: $protein, fat: $fat")
//            val today = LocalDate.now().minusDays(1).toString()
//            var eatingList = eatingViewModel.todayEating.value
//
//            if(eatingList.isEmpty()){
//                return Result.success()
//            }
//
//            var nutrientMap = mutableMapOf<String, Nutrient>()
//
//            for(e in eatingList){
//                if(nutrientMap.containsKey(e.personId)){  //id값이 있는 경우, 기존 영양성분에 더하기
//                    val nutrient = nutrientMap[e.personId]!!
//                    nutrientMap[e.personId] = Nutrient(
//                        nutrient.carbohydrate + (e.carbohydrate * (e.gram/100)),
//                        nutrient.protein + (e.protein * (e.gram/100)),
//                        nutrient.fat + (e.fat * (e.gram/100))
//                    )
//                } else {
//                    nutrientMap[e.personId] = Nutrient(
//                        e.carbohydrate * (e.gram/100),
//                        e.protein * (e.gram/100),
//                        e.fat * (e.gram/100)
//                    )
//                }
//            }
//
//            for(n in nutrientMap){
//                loginViewModel.updatePersonIntake(n.key, mapOf(today to n.value))
//            }
//
//            eatingViewModel.deleteAllEating()


            Result.success()
        } catch (e:Exception){
            Result.failure()
        }
    }
}
