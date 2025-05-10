package com.example.data.datasourceimpl.remotedatasource

import com.example.data.datasource.remote.FirebaseAuthDataSource
import com.example.domain.model.AuthResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSourceImpl @Inject constructor(
    private val firebaseAuth:FirebaseAuth ,
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
        } catch (ex: FirebaseAuthInvalidUserException) {
            AuthResponse.Failure(ex, "User not found")

        } catch (ex: FirebaseAuthInvalidCredentialsException) {
            AuthResponse.Failure(ex, "Wrong password")

        } catch (ex: FirebaseAuthException) {
            AuthResponse.Failure(ex, "Authentication failed: ${ex.message}")

        } catch (ex: Exception) {
            AuthResponse.Failure(ex, "Unknown error: ${ex.localizedMessage}")
        }
    }

    override suspend fun signInWithGoogle(idToken: String): AuthResponse<FirebaseUser> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val result  = firebaseAuth.signInWithCredential(credential).await()
            val user = result.user
            if(user!=null){
                AuthResponse.Success(user)
            }else{
                AuthResponse.Failure(Exception() ,"User is Null " )
            }

        }catch (ex:Exception){
            AuthResponse.Failure(ex , ex.localizedMessage!!)
        }



    }

    override suspend fun signUpWithGoogle(idToken: String): AuthResponse<FirebaseUser> {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    override fun logOut() {
        firebaseAuth.signOut()
    }

}