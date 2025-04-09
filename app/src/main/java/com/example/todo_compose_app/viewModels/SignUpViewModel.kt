package com.example.todo_compose_app.viewModels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class SignUpViewModel: ViewModel() {

    var fullNameText by mutableStateOf("")
        private set
    fun setFullNameValue(fullNameValue: String){
        fullNameText = fullNameValue
    }

    var emailAddressText by mutableStateOf("")
        private set
    fun setEmailAddressValue(emailAddressValue: String){
        emailAddressText = emailAddressValue
    }

    var passwordText by mutableStateOf("")
        private set
    fun setPasswordValue(passwordValue: String){
        passwordText = passwordValue
    }


    var confirmPasswordText by mutableStateOf("")
        private set
    fun setConfirmPasswordValue(confirmPasswordValue: String){
        confirmPasswordText = confirmPasswordValue
    }

}