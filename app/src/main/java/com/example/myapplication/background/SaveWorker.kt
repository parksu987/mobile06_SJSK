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

@HiltWorker
class SaveWorker @AssistedInject constructor(
    @Assisted ctx:Context,
    @Assisted params: WorkerParameters,
    private var loginViewModel: LoginViewModel,
    private var eatingViewModel: EatingViewModel
) : Worker(ctx, params) {
    override fun doWork(): Result {
        return try {
            val today = LocalDate.now().minusDays(1).toString()
            var eatingList = eatingViewModel.todayEating.value

            if(eatingList.isEmpty()){
                return Result.success()
            }

            var nutrientMap = mutableMapOf<String, Nutrient>()

            for(e in eatingList){
                if(nutrientMap.containsKey(e.personId)){  //id값이 있는 경우, 기존 영양성분에 더하기
                    val nutrient = nutrientMap[e.personId]!!
                    nutrientMap[e.personId] = Nutrient(
                        nutrient.carbohydrate + (e.carbohydrate * (e.gram/100)),
                        nutrient.protein + (e.protein * (e.gram/100)),
                        nutrient.fat + (e.fat * (e.gram/100))
                    )
                } else {
                    nutrientMap[e.personId] = Nutrient(
                        e.carbohydrate * (e.gram/100),
                        e.protein * (e.gram/100),
                        e.fat * (e.gram/100)
                    )
                }
            }

            for(n in nutrientMap){
                loginViewModel.updatePersonIntake(n.key, mapOf(today to n.value))
            }

            eatingViewModel.deleteAllEating()

            Log.d("workManager", "skf")

            Result.success()
        } catch (e:Exception){
            Result.failure()
        }
    }

//    class Factory @Inject constructor(
//        private val loginViewModelProvider: Provider<LoginViewModel>,
//        private val eatingViewModelProvider: Provider<EatingViewModel>
//    ) : WorkerFactory() {
//        override fun createWorker(
//            appContext: Context,
//            workerClassName: String,
//            workerParameters: WorkerParameters
//        ): ListenableWorker? {
//            return SaveWorker(
//                appContext,
//                workerParameters,
//                loginViewModelProvider.get(),
//                eatingViewModelProvider.get()
//            )
//        }
//    }
}
