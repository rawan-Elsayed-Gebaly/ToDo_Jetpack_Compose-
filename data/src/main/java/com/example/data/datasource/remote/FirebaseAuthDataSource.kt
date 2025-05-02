package com.example.data.datasource.remote

import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthDataSource{

     suspend fun signUp(email:String , password:String): AuthResponse<FirebaseUser>
    suspend fun logIn(email:String , password:String ):AuthResponse<FirebaseUser>
    fun getCurrentUser() :FirebaseUser?
     fun logOut()


}