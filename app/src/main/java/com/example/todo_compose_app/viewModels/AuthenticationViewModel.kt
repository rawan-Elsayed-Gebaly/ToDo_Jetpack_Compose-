package com.example.todo_compose_app.viewModels

import android.util.Patterns
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.domain.model.AuthResponse
import com.example.domain.usecases.AuthenticationUseCase
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthenticationViewModel @Inject constructor(
    private val firebaseAuthUseCase: AuthenticationUseCase
) : ViewModel() {


    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    private val _googleAuthState = MutableStateFlow<GoogleAuthUiState>(GoogleAuthUiState.Idle)
    val googleAuthState :StateFlow<GoogleAuthUiState> = _googleAuthState
    private suspend fun registerValidation(name: String, email: String, password: String): Boolean {

        if (name.isEmpty()) {
            _authState.value = AuthUiState.Failure("Name cannot be Empty")
            return false
        }
        if (email.isBlank()) {
            _authState.value = AuthUiState.Failure("Email cannot be empty")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthUiState.Failure("Invalid email format")
            return false
        }
        if (password.length < 6) {
            _authState.value = AuthUiState.Failure("Password can't be less than 6 characters ")
            return false
        }
        if (_confirmPasswordText.value != _passwordText.value) {
            _authState.value = AuthUiState.Failure("Passwords don't match")
            return false
        }


        if (!_termsAccepted.value) {
            _authState.value = AuthUiState.Failure("Please accept the Terms & Conditions")
            return false
        }

        return true

    }


     suspend fun signInWithGoogle(idToken:String){
        viewModelScope.launch {
            _googleAuthState.value = GoogleAuthUiState.Loading
            val result = firebaseAuthUseCase.signInWithGoogle(idToken)
            _googleAuthState.value = when (result) {
                is AuthResponse.Success -> GoogleAuthUiState.Success(result.data)
                is AuthResponse.Failure -> GoogleAuthUiState.Failure(result.message)
                is AuthResponse.Loading -> GoogleAuthUiState.Loading
            }

    }
    }
    private suspend fun loginValidation(email: String, password: String): Boolean {
        if (email.isBlank()) {
            _authState.value = AuthUiState.Failure("Email cannot be empty")
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _authState.value = AuthUiState.Failure("Invalid email format")
            return false
        }
        if (password.isBlank()) {
            _authState.value = AuthUiState.Failure("Password cannot be empty")
            return false
        }
        return true
    }

    fun onSignUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            if (registerValidation(name, email, password)) {
                _authState.value = AuthUiState.Loading

                when (
                    val result = firebaseAuthUseCase.signUp(email, password)
                ) {

                    is AuthResponse.Success -> {
                        val uid = result.data.uid
                        _authState.value = AuthUiState.Success(uid)
                    }

                    is AuthResponse.Failure -> {
                        _authState.value = AuthUiState.Failure(result.exception?.localizedMessage!!)
                    }

                    is AuthResponse.Loading -> {
                        _authState.value = AuthUiState.Loading
                    }

                    else -> {}
                }

            }
        }


    }

    fun onLogIn(email: String, password: String) {
        viewModelScope.launch {
            if (loginValidation(email, password)) {
                _authState.value = AuthUiState.Loading
                when (val result = firebaseAuthUseCase.logIn(email, password)) {
                    is AuthResponse.Failure -> {
                        val errorMessage = when (val exception = result.exception) {
                            is FirebaseAuthInvalidCredentialsException -> "Incorrect password."
                            is FirebaseAuthInvalidUserException -> "No account found with this email."
                            else -> exception?.localizedMessage ?: "An unknown error occurred."
                        }
                        _authState.value = AuthUiState.Failure(errorMessage)

                    }

                    AuthResponse.Loading -> {
                        _authState.value = AuthUiState.Loading
                    }

                    is AuthResponse.Success -> {
                        val uid = result.data.uid
                        _authState.value = AuthUiState.Success(uid)
                    }


                }


            }

        }
    }


    private val _fullNameText = mutableStateOf("")
    val fullNameText: State<String> = _fullNameText
    fun setFullNameValue(value: String) {
        _fullNameText.value = value
    }


    private val _emailAddressText = mutableStateOf("")
    val emailAddressText: State<String> = _emailAddressText
    fun setEmailAddress(value: String) {
        _emailAddressText.value = value
    }


    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText
    fun setPasswordText(value: String) {
        _passwordText.value = value
    }

    private val _confirmPasswordText = mutableStateOf("")
    val confirmPasswordText: State<String> = _confirmPasswordText
    fun setConfirmPasswordText(value: String) {
        _confirmPasswordText.value = value
    }


    private var _termsAccepted = mutableStateOf(false)
    val termsAccepted: State<Boolean> = _termsAccepted
    fun setTermsAccepted(value: Boolean) {
        _termsAccepted.value = value
    }


    private val _loginEmailAddressText = mutableStateOf("")
    val loginEmailAddressText: State<String> = _loginEmailAddressText
    fun setLoginEmailAddress(value: String) {
        _loginEmailAddressText.value = value
    }


    private val _loginPasswordText = mutableStateOf("")
    val loginPasswordText: State<String> = _loginPasswordText
    fun setLoginPasswordText(value: String) {
        _loginPasswordText.value = value
    }


}