package com.example.final_projet_android_4a.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_projet_android_4a.domain.entity.User
import com.example.final_projet_android_4a.domain.usecase.CreateUserUseCase
import com.example.final_projet_android_4a.domain.usecase.GetUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainViewModel(
    private val createUserUseCase: CreateUserUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel(){
    val loginLiveData: MutableLiveData<LoginStatus> = MutableLiveData()

    fun onClickedCreate(emailUser: String, password: String){
        viewModelScope.launch(Dispatchers.IO) {
            val newUser = User(emailUser)
            createUserUseCase.invoke(newUser)
        }
    }

    fun onClickedLogin(emailUser: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val user = getUserUseCase.invoke(emailUser)
            val loginStatus = if(user != null){
                LoginSuccess(user.email)
            } else {
                LoginError
            }
            withContext(Dispatchers.Main){
                loginLiveData.value = loginStatus
            }
        }
    }
}