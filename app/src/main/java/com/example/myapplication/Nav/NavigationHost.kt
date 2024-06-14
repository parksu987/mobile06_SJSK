package com.example.myapplication.Nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.DB.Food
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import com.example.myapplication.screen.myPage
import com.example.myapplication.screen.todayEating
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.compare.MainCompare
import com.example.myapplication.compare.ProductViewModel
import com.example.myapplication.login.LoginActivityCompose
import com.example.myapplication.login.SignupActivityCompose
import com.example.myapplication.viewmodel.LoginViewModel
import java.time.LocalDate

@Composable
fun NavigationHost(navController:NavHostController, eatingViewModel: EatingViewModel, productViewModel: ProductViewModel, loginViewModel: LoginViewModel) {
    val date = LocalDate.now()
    val intake = mapOf<String, Nutrient>(
        date.toString()  to Nutrient(150.0, 40.0, 30.0)

    )
    val S = Person("id", "pw", "name", 24, 153.0, 41.0, 1600, 210.0, 40.0, 45.0, intake)
    var foodList = rememberSaveable { mutableStateOf(listOf<Food>()) }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ){
        composable(NavRoutes.Home.route){
            Home()
        }
        composable(NavRoutes.Comparison.route){
            MainCompare(productViewModel)
        }
        composable(NavRoutes.Login.route){
            LoginActivityCompose(navController, loginViewModel)
        }
        composable(NavRoutes.SignUp.route){
            SignupActivityCompose(navController, loginViewModel)
        }
        composable(NavRoutes.TodayEating.route){
            todayEating(S.kcal, S.carbohydrate, S.protein, S.fat, eatingViewModel)
        }
        composable(NavRoutes.MyPage.route) {
            val person = loginViewModel.person.collectAsState().value
            if (person != null) {
                myPage(person, { })
            } else {
                Log.d("MyPage", "No user data")
            }
        }
    }
}