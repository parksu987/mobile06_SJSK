package com.example.myapplication.Nav


sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("Home")
    object Comparison: NavRoutes("Comparison")
    object TodayEating: NavRoutes("TodayEating")
    object MyPage: NavRoutes("MyPage")
    object Login: NavRoutes("Login")
    object SignUp: NavRoutes("SignUp")
    object Welcome: NavRoutes("Welcome")
}