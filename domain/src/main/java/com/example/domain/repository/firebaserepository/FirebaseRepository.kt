package com.example.domain.repository.firebaserepository

import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseUser

interface FirebaseRepository {

    suspend fun signUp(email:String , password:String): AuthResponse<FirebaseUser>
    suspend fun logIn(email:String , password:String ): AuthResponse<FirebaseUser>
    suspend fun signInWithGoogle(idToken: String): AuthResponse<User>
    suspend fun signUpWithGoogle(idToken: String): AuthResponse<FirebaseUser>
    fun getCurrentUser() : FirebaseUser?
    fun logOut()

}