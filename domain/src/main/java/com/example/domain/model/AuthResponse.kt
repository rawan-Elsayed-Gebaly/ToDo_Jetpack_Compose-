package com.example.domain.model

sealed class AuthResponse<out T> {

    data object Loading : AuthResponse<Nothing>()
    data class Failure<out T>(
        val exception: Exception?,
        val message: String
    ) : AuthResponse<T>()
    data class Success<out T>(val data: T) : AuthResponse<T>()

    //Helper through the UI
    val isLoading get() = this is Loading
    val isFailure get() = this is Failure<*>
    val isSuccess get() = this is Success<*>

}