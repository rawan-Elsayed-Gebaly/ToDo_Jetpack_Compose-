package com.example.domain.model

data class User(
    val userId: String,
    var name: String,
    val email:String,
    val password: String?,
    val photoUrl: String?
)
