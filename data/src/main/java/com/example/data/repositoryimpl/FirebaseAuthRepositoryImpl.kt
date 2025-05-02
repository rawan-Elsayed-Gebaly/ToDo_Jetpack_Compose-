package com.example.data.repositoryimpl

import com.example.data.datasource.remote.FirebaseAuthDataSource
import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.example.domain.repository.firebaserepository.FirebaseRepository
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuthDataSource: FirebaseAuthDataSource
):FirebaseRepository {
    override suspend fun signUp(email: String, password: String): AuthResponse<FirebaseUser> {
        return firebaseAuthDataSource.signUp(email , password)
    }

    override suspend fun logIn(email: String, password: String): AuthResponse<FirebaseUser> {
        return firebaseAuthDataSource.logIn(email , password)
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuthDataSource.getCurrentUser()
    }

    override fun logOut() {
        firebaseAuthDataSource.logOut()
    }
}