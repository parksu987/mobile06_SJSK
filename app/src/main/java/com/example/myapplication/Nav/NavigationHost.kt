package com.example.myapplication.Nav

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.screen.myPage
import com.example.myapplication.screen.todayEating
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.compare.MainCompare
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.login.LoginActivityCompose
import com.example.myapplication.login.SignupActivityCompose
import com.example.myapplication.screen.SearchScreen
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.SearchViewModel
import java.time.LocalDate

@Composable
fun NavigationHost(navController:NavHostController,
                   eatingViewModel: EatingViewModel,
                   productViewModel: ProductViewModel,
                   loginViewModel: LoginViewModel,
                   searchViewModel: SearchViewModel
) {
    val date = LocalDate.now()
    val intake = mapOf<String, Nutrient>(
        date.toString()  to Nutrient(150.0, 40.0, 30.0)
    )
    val person = loginViewModel.person.collectAsState().value
//    val S = Person("id", "pw", "name", 24, 153.0, 41.0, 1600, 210.0, 40.0, 45.0, intake)


    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ){
        composable(NavRoutes.Home.route){
            SearchScreen(searchViewModel, productViewModel, navController)
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
            if (person != null) {
                todayEating(
                    person,
                    eatingViewModel = eatingViewModel,
                    searchViewModel = searchViewModel,
                    loginViewModel
                )
            } else {
                Log.d("todayEating", "No user data")
            }
        }
        composable(NavRoutes.MyPage.route) {

            if (person != null) {
                myPage(person, loginViewModel, { })
            } else {
                Log.d("MyPage", "No user data")
            }
        }
    }
}