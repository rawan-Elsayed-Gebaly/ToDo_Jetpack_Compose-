package com.example.todo_compose_app.viewModels

import com.example.domain.model.User

sealed class GoogleAuthUiState {

    data object Idle:GoogleAuthUiState()
    data object Loading: GoogleAuthUiState()
    data class Success(val user: User):GoogleAuthUiState()
    data class Failure(val message:String):GoogleAuthUiState()

}