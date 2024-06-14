package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

    // 로그인 상태를 관리하는 StateFlow 추가
    private val _loginStatus = MutableStateFlow<LoginStatus?>(null)
    val loginStatus: StateFlow<LoginStatus?> = _loginStatus.asStateFlow()
    // 중복 확인 결과 상태
    private val _isIdDuplicated = MutableStateFlow(false)
    val isIdDuplicated: StateFlow<Boolean> = _isIdDuplicated.asStateFlow()

    fun checkLogin(userId: String, password: String) {
        viewModelScope.launch {
            repository.checkLogin(userId, password).collect { person ->
                _person.value = person
                _loginStatus.value = if (person != null) LoginStatus.SUCCESS else LoginStatus.FAILURE
            }
        }
    }

    // ID 중복 확인 함수
    fun checkIdDuplication(id: String) {
        viewModelScope.launch {
            repository.checkIdDuplication(id).collect { isDuplicated ->
                _isIdDuplicated.value = isDuplicated
            }
        }
    }

  fun signUp(name : String, id : String, pw :String) {
        viewModelScope.launch {
            repository.signUp(name, id, pw).collect { isDuplicated ->
                _isIdDuplicated.value = isDuplicated
            }
        }
    }

}

enum class LoginStatus {
    SUCCESS,
    FAILURE
}









