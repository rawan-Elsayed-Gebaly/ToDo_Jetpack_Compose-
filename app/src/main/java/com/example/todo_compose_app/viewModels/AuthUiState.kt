package com.example.todo_compose_app.viewModels

import com.example.domain.model.AuthResponse

sealed class AuthUiState {

    data object Idle:AuthUiState()
    data object Loading: AuthUiState()
    data class Success(val userId:String):AuthUiState()
    data class Failure(val message:String):AuthUiState()

}