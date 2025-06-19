package com.example.todo_compose_app.screens.signup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavController
import com.example.todo_compose_app.R
import com.example.todo_compose_app.viewModels.authviewmodel.AuthenticationViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todo_compose_app.screens.login.OptionalWays
import com.example.todo_compose_app.screens.login.launchGoogleSignIn
import com.example.todo_compose_app.viewModels.authviewmodel.AuthUiState
import com.example.todo_compose_app.viewModels.authviewmodel.GoogleAuthUiState


@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthenticationViewModel = hiltViewModel(), // inject ViewModel

) {
    SignUpContent(viewModel, Modifier, navController)

}


@Composable
fun SignUpContent(
    viewModel: AuthenticationViewModel,
    modifier: Modifier = Modifier,
    navController: NavController,
) {
//


    Column(
        modifier = Modifier
            .padding(18.dp)
            .fillMaxSize()
            .background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

        var hasFocus by remember { mutableStateOf(false) }

        TextFieldItem(
            value = viewModel.fullNameText.value,
            onValueChanged = { value ->
                viewModel.setFullNameValue(value)
            },
            placeHolderText = "Full Name",
            modifier = Modifier
        )
        TextFieldItem(
            value = viewModel.emailAddressText.value,
            onValueChanged = { value ->
                viewModel.setEmailAddress(value)

            },
            placeHolderText = "Email Address",
            modifier = Modifier
        )


        var passwordVisible by remember { mutableStateOf(false) }
        var confirmPasswordVisible by remember { mutableStateOf(false) }


        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.passwordText.value,
            onValueChange = { value ->
                viewModel.setPasswordText(value)

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

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.confirmPasswordText.value,
            onValueChange = { value ->
                viewModel.setConfirmPasswordText(value)


            },
            placeholder = {
                Text(
                    text = "Confirm Password",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            },
            shape = RoundedCornerShape(12.dp),
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (confirmPasswordVisible)
                    painterResource(R.drawable.eyeclosed)
                else
                    painterResource(R.drawable.eyeclosed)

                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        painter = image,
                        contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.blue) ,
                focusedContainerColor = Color.White ,
                unfocusedContainerColor = Color.White
            )

        )

        DrawingCheckBox(modifier, viewModel)
        DrawingTheSignUpBtn(viewModel, modifier, navController)
        DrawingSignUpWays(
            viewModel , navController
        )
        OptionalWays(
            navController =  navController ,
            route = "login" ,
            text = "Already have an account? " ,
            clickableText = "Log In"
        )
    }
}

@Composable
fun TextFieldItem(
    value: String,
    onValueChanged: (String) -> Unit,
    placeHolderText: String,
    modifier: Modifier
) {
    OutlinedTextField(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        value = value,
        onValueChange = onValueChanged,
        placeholder = {
            Text(
                text = placeHolderText,
                style = TextStyle(fontSize = 12.sp, color = Color.Gray)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = colorResource(R.color.blue),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White
        )
    )

}

@Composable
fun DrawingSignUpToDoIc(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.size(70.dp),
        painter = painterResource(R.drawable.ic_to_do),
        contentDescription = "",
        tint = Color.Unspecified

    )
}


@Composable
fun DrawingCheckBox(
    modifier: Modifier,
    viewModel: AuthenticationViewModel
) {

    val isChecked by viewModel.termsAccepted
    Row(
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                viewModel.setTermsAccepted(it)
            },
            colors = CheckboxDefaults.colors(
                colorResource(R.color.blue)
            )

        )
        Text(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray,
                        fontFamily = fontFamily,
                    )
                ) {
                    append("I have read, understood & agreed to ToDo’s ")
                }
                withStyle(
                    style = SpanStyle(
                        color = colorResource(R.color.blue),
                        fontFamily = fontFamily,
                    )
                ) {
                    append("Privacy Policy, Terms & Condition")
                }
            },

            textAlign = TextAlign.Center
        )

    }
}


@Composable
fun DrawingTheSignUpBtn(
    viewModel: AuthenticationViewModel,
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val authState by viewModel.authState.collectAsState()

    var name = viewModel.fullNameText.value
    var email = viewModel.emailAddressText.value
    var password = viewModel.passwordText.value
    var confirmPassword = viewModel.confirmPasswordText.value

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

            viewModel.onSignUp(name, email, password)

        },
        enabled = authState !is AuthUiState.Loading,
        shape = RoundedCornerShape(8.dp)

    ) {
        if (authState is AuthUiState.Loading) {
            CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.White)
        } else {
            Text(
                modifier = modifier.padding(8.dp),
                text = "Sign Up",
                fontSize = 16.sp
            )
        }

    }

    when (authState) {
        is AuthUiState.Loading -> CircularProgressIndicator()
        is AuthUiState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate("emailVerification") {
                    popUpTo("signUp") { inclusive = true }
                }
            }
        }

        is AuthUiState.Failure -> {
            Text(
                text = (authState as AuthUiState.Failure).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        else -> {}
    }

}


@Composable
fun DrawingSignUpWays(
    viewModel: AuthenticationViewModel,
    navController: NavController,
) {
    val context = LocalContext.current // ✅ this is the correct way


    val googleAuthState by viewModel.googleAuthState.collectAsState()

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
    ) {

        Button(
            onClick = {

                launchGoogleSignIn(context =  context , viewModel)
            },
            modifier = Modifier
                .weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            ),
            border = BorderStroke(0.5.dp, colorResource(R.color.light_gray))

        ) {

            Icon(

                painter = painterResource(R.drawable.google_logo),
                contentDescription = "",
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Unspecified
            )

            Text(
                text = "Sign up with Google",
                fontSize = 10.sp,
                color = colorResource(R.color.gray)
            )



        }

        Spacer(
            modifier = Modifier.width(12.dp)
        )

        Button(
            onClick = {
            },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                Color.Transparent
            ),
            border = BorderStroke(0.5.dp, colorResource(R.color.light_gray)),
        ) {
            Icon(
                painter = painterResource(R.drawable.facebook_logo),
                contentDescription = "",
                modifier = Modifier
                    .padding(end = 6.dp)
                    .size(width = 20.dp, height = 20.dp),
                tint = Color.Unspecified
            )

            Text(
                text = "Sign up with Facebook",
                fontSize = 10.sp,
                color = colorResource(R.color.gray)
            )

        }

    }

    when (googleAuthState) {
        is GoogleAuthUiState.Loading -> CircularProgressIndicator()
        is GoogleAuthUiState.Success -> {
            LaunchedEffect(Unit) {
                navController.navigate("emailVerification") {
                    popUpTo("signUp") { inclusive = true }
                }
            }
        }

        is GoogleAuthUiState.Failure -> {
            Text(
                text = (googleAuthState as GoogleAuthUiState.Failure).message,
                color = Color.Red,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        else -> {}

}
}



