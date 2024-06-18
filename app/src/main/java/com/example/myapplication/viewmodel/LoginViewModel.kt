package com.example.myapplication.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapplication.DB.Nutrient
import com.example.myapplication.DB.Person
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModelFactory(private val repository: LoginRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class LoginViewModel(private val repository: LoginRepository): ViewModel() {
    private val _person = MutableStateFlow<Person?>(null)
    val person: StateFlow<Person?> = _person.asStateFlow()

    var loginStatus = mutableStateOf(false)

    fun checkLogin(userId: String, password: String) {
        viewModelScope.launch {
            repository.checkLogin(userId, password).collect { person ->
                _person.value = person
            }
        }
    }

    fun setLoginStatus(userID:String, password:String):Boolean {
        if(checkLogin(userID, password) != null){
            loginStatus.value = true
            return true
        }
        return false
    }

    fun updatePersonIntake(intake: Map<String, Nutrient>) {
        repository.addPersonIntake(intake)
    }

    fun updatePersonNutrient(kcal: Int, nutrient: Nutrient) {
        repository.updatePersonNutrient(kcal, nutrient)
    }

}










