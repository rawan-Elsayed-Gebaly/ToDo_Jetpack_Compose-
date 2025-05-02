package com.example.data.datasourceimpl.remotedatasource

import com.example.data.datasource.remote.FirebaseAuthDataSource
import com.example.domain.model.AuthResponse
import com.example.domain.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
    private val firebaseAuth:FirebaseAuth
): FirebaseAuthDataSource{


    override suspend fun signUp(email: String, password: String): AuthResponse<FirebaseUser> {
        return try {
            if (email.isBlank() || password.isBlank()) {
                return AuthResponse.Failure(Exception() ,"Email or password cannot be null")
            }
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val newUser = result.user
            if(newUser!=null ){
                AuthResponse.Success(newUser)
            }else{
                AuthResponse.Failure(Exception() , "User is Null")
            }
        }catch (ex:Exception){
             AuthResponse.Failure(ex , ex.localizedMessage!!)
        }
    }


    override suspend fun logIn(email: String, password: String): AuthResponse<FirebaseUser> {
        return try {
            if (email.isBlank() || password.isBlank()) {
                return AuthResponse.Failure(Exception() ,"Email or password cannot be null")
            }
           val result= firebaseAuth.signInWithEmailAndPassword(email , password).await().user
            if(result !=null){
                AuthResponse.Success(result)
            }else{
                AuthResponse.Failure(Exception() , "User doesn't exist")
            }
        }catch (ex:Exception){
            AuthResponse.Failure(Exception(ex) ,ex.localizedMessage!!)
        }

    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }

}