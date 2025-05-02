package com.example.domain.usecases

import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.example.domain.repository.firebaserepository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class AuthenticationUseCase @Inject constructor (
    private val firebaseRepository: FirebaseRepository
) {

    suspend fun signUp(email:String , password:String):AuthResponse<FirebaseUser>{
        return firebaseRepository.signUp(email , password)
    }

    suspend fun logIn(email:String , password:String):AuthResponse<FirebaseUser>{
        return firebaseRepository.logIn(email , password)
    }

    fun logout(){
        firebaseRepository.logOut()
    }

    fun getCurrentUser():FirebaseUser?{
        return firebaseRepository.getCurrentUser()
    }

}