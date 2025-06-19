package com.example.todo_compose_app.screens.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todo_compose_app.R
import com.example.todo_compose_app.screens.signup.DrawingSignUpToDoIc
import com.example.todo_compose_app.screens.signup.TextFieldItem
import com.example.todo_compose_app.screens.signup.fontFamily
import com.example.todo_compose_app.viewModels.authviewmodel.AuthUiState
import com.example.todo_compose_app.viewModels.authviewmodel.AuthenticationViewModel
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LogInScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel()
) {
    LogInContent(Modifier, navController, viewModel)
}

@Composable
fun LogInContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: AuthenticationViewModel
) {
    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        DrawingToDoIcon(modifier)

        DrawingTextFieldsItems(modifier, viewModel)

        DrawingLogInBtn(modifier, navController, viewModel)

//        DrawingSignUpWays(
//
//        )

        Row {

            DrawingForgetPassText(navController)
            Spacer(
                modifier = Modifier.width(20.dp)
            )
            OptionalWays(
                navController = navController,
                route = "signUp",
                text = "Don't have an account? ",
                clickableText = "Sing Up"
            )


        }


    }

}

@Composable
fun DrawingForgetPassText(navController: NavController) {
    Text(
        text = "Forgot password?",
        fontSize = 12.sp,
        color = colorResource(R.color.blue),
        fontFamily = fontFamily,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier
            .padding(4.dp)
            .clickable {
                navController.navigate("emailVerification")
            },

        )

}

@Composable
fun OptionalWays(
    navController: NavController,
    route: String,
    text: String,
    clickableText: String

) {


    Text(
        text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(text)
            }

            withStyle(
                SpanStyle(
                    fontSize = 12.sp,
                    color = colorResource(R.color.blue),
                    fontFamily = fontFamily,
                    fontWeight = FontWeight.SemiBold
                )
            ) {
                append(clickableText)
            }
        },
        modifier = Modifier
            .padding(4.dp)
            .clickable { navController.navigate(route) },
        textAlign = TextAlign.Center,

        )

}

@Composable
fun DrawingLogInBtn(
    modifier: Modifier,
    navController: NavController,
    viewModel: AuthenticationViewModel
) {


    val authState by viewModel.authState.collectAsState()

    val loginEmail = viewModel.loginEmailAddressText.value
    val loginPass = viewModel.loginPasswordText.value

    Button(

        modifier = modifier
            .fillMaxWidth()
            .padding(
                vertical = 22.dp
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue) // Set the background color to blue
        ),
        onClick = {
            viewModel.onLogIn(loginEmail, loginPass)

        },
        shape = RoundedCornerShape(8.dp),
        enabled = authState !is AuthUiState.Loading,

        ) {
        if (authState is AuthUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
        } else {
            Text(
                modifier = modifier.padding(8.dp),
                text = "Log In",
                fontSize = 16.sp
            )
        }
    }
    when(authState){
        is AuthUiState.Failure -> {
            Text(
                text = (authState as AuthUiState.Failure).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        AuthUiState.Loading -> CircularProgressIndicator()
        is AuthUiState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate("emailVerification"){
                    popUpTo("signUp") { inclusive = true }
                }
            }
        }

        else -> {}
    }

}

@Composable
fun DrawingToDoIcon(modifier: Modifier) {

    Row(
        modifier = Modifier.padding(22.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        DrawingSignUpToDoIc(modifier)
        Text(
            modifier = modifier.padding(start = 8.dp),
            text = "To Do",
            style = TextStyle(
                fontSize = 18.sp,
                color = colorResource(R.color.blue),
                fontWeight = FontWeight.Bold
            )
        )
    }


}

@Composable
fun DrawingTextFieldsItems(
    modifier: Modifier,
    viewModel: AuthenticationViewModel

) {
    TextFieldItem(
        value = viewModel.loginEmailAddressText.value,
        onValueChanged = { value ->
            viewModel.setLoginEmailAddress(value)
        },
        placeHolderText = "Email Address",
        modifier = Modifier
    )

    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        value = viewModel.loginPasswordText.value,
        onValueChange = { value ->
            viewModel.setLoginPasswordText(value)
        },
        placeholder = {
            Text(
                text = "Password",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
        },
        shape = RoundedCornerShape(12.dp),
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible)
                painterResource(R.drawable.eyeclosed)
            else
                painterResource(R.drawable.eyeclosed)

            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = image,
                    contentDescription = if (passwordVisible) "Hide password" else "Show password"
                )
            }
        },
        singleLine = true,

        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorResource(R.color.blue),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )



    )
}
fun launchGoogleSignIn(context: Context, viewModel: AuthenticationViewModel) {
    val googleIdOption = GetGoogleIdOption.Builder()
        .setServerClientId(context.getString(R.string.default_web_client_id))
        .setFilterByAuthorizedAccounts(true)
        .setAutoSelectEnabled(true)
        .build()

    val request = GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build()

    val credentialManager = CredentialManager.create(context)

    CoroutineScope(Dispatchers.Main).launch {
        try {
            val result = credentialManager.getCredential(context , request)
            val credential = result.credential
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                viewModel.signInWithGoogle(googleIdTokenCredential.idToken)
            }
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Sign-in failed", e)
        }
    }
}
