package com.example.myapplication.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.ku.rememberViewModelStoreOwner
import com.example.myapplication.Nav.NavRoutes
import com.example.myapplication.viewmodel.LoginStatus
import com.example.myapplication.viewmodel.LoginViewModel
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen(navController: NavHostController, loginViewModel: LoginViewModel) {
    val person by loginViewModel.person.collectAsState()
    val loginStatus by loginViewModel.loginStatus.collectAsState()

    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Welcome Screen",
            fontSize = 40.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Text(
            text = "${person?.name}님 환영합니다.",
            fontSize = 30.sp,
            fontWeight = FontWeight.ExtraBold
        )

        LaunchedEffect(key1 = Unit) {  //버튼 안누르고 자동으로 이동하고 싶을때 (버튼 이용하면 rememberState coroutine)
            delay(2000)  //2초 후에 이동시킴(suspend함수: composable함수에서 호출 불가)

            navController.navigate(NavRoutes.Home.route)
        }
        if(loginStatus == LoginStatus.SUCCESS) {
            navController.navigate(NavRoutes.Home.route) {
                popUpTo(NavRoutes.Login.route){
                    inclusive = true
                }
                launchSingleTop = true  //로그인까지 포함시켜서 제거하겠다는 의미
            }
        }
    }
}