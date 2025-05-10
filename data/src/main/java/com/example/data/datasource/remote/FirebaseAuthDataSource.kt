package com.example.data.datasource.remote

import com.example.domain.model.AuthResponse
import com.google.firebase.auth.FirebaseUser

interface FirebaseAuthDataSource{

     suspend fun signUp(email:String , password:String): AuthResponse<FirebaseUser>
    suspend fun logIn(email:String , password:String ):AuthResponse<FirebaseUser>
    suspend fun signInWithGoogle(idToken: String): AuthResponse<FirebaseUser>
    suspend fun signUpWithGoogle(idToken: String): AuthResponse<FirebaseUser>

    fun getCurrentUser() :FirebaseUser?
     fun logOut()


}