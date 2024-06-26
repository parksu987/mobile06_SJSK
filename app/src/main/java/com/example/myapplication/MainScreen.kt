package com.example.ku

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.myapplication.Nav.BottomNavigationBar
import com.example.myapplication.Nav.NavigationHost
import com.example.myapplication.background.SaveWorkManager
import com.example.myapplication.viewmodel.ProductViewModel
import com.example.myapplication.firestore.RetrofitClient
import com.example.myapplication.roomDB.EatingDatabase
import com.example.myapplication.viewmodel.EatingRepository
import com.example.myapplication.viewmodel.EatingViewModel
import com.example.myapplication.viewmodel.EatingViewModelFactory
import com.example.myapplication.viewmodel.LoginRepository
import com.example.myapplication.viewmodel.LoginStatus
import com.example.myapplication.viewmodel.LoginViewModel
import com.example.myapplication.viewmodel.LoginViewModelFactory
import com.example.myapplication.viewmodel.Repository
import com.example.myapplication.viewmodel.SearchViewModel
import com.example.myapplication.viewmodel.ViewModelFactory
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainScreen(navController: NavHostController) {
    val context = LocalContext.current
    val navStoreOwner = rememberViewModelStoreOwner()
    val eatingdb = EatingDatabase.getEatingDatabase(context)
    val eatingViewModel: EatingViewModel = viewModel(factory = EatingViewModelFactory(  EatingRepository(eatingdb)  ) )
    val productViewModel  = ProductViewModel()
    val db = Firebase.firestore

    val searchViewModel: SearchViewModel = viewModel(factory = ViewModelFactory(
        Repository(
        RetrofitClient.instance)
        )
    )


    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        val loginViewModel: LoginViewModel = viewModel(
            factory = LoginViewModelFactory(
                LoginRepository(db)
            ),
            viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current
        )

        LaunchedEffect(Unit) {
            loginViewModel.setContext(context)
        }

        val loginStatus by loginViewModel.loginStatus.collectAsState()

        Scaffold(
            bottomBar = {
                Log.d("MainScreen", "loginViewModel.loginStatus.value = ${loginViewModel.loginStatus.value}")
                if (loginStatus == LoginStatus.SUCCESS)
                    BottomNavigationBar(navController)
            }
        ) { contentPadding ->
            Column(
                modifier = Modifier.padding(contentPadding)
            ) {
                NavigationHost(
                    navController = navController,
                    eatingViewModel = eatingViewModel,
                    productViewModel = productViewModel,
                    loginViewModel,
                    searchViewModel
                )
            }
        }

        val workManager = SaveWorkManager(context, loginViewModel = loginViewModel, eatingViewModel = eatingViewModel)
        workManager.makeNotifyWorkRequest()
    }
}