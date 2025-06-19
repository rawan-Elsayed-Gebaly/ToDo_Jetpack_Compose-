package com.example.todo_compose_app.screens

import androidx.annotation.ColorRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todo_compose_app.R
import com.example.todo_compose_app.viewModels.SignUpViewModel

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = viewModel()
) {
    DrawingTextFields(viewModel, Modifier , navController)
}



@Composable
fun DrawingTextFields(
    viewModel: SignUpViewModel,
    modifier: Modifier = Modifier ,
    navController: NavController
) {
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

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            value = viewModel.fullNameText,
            onValueChange = { value ->
                viewModel.setFullNameValue(value)
            },
            placeholder = {
                Text(
                    text = "Full Name",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            },



            )


        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.emailAddressText,
            onValueChange = { value ->
                viewModel.setEmailAddressValue(value)
            },
            placeholder = {
                Text(
                    text = "Email Address",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            },
            shape = RoundedCornerShape(12.dp),

            )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.passwordText,
            onValueChange = { value ->
                viewModel.setPasswordValue(value)
            },
            placeholder = {
                Text(
                    text = "Password",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            },
            shape = RoundedCornerShape(12.dp),

            )

        OutlinedTextField(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            value = viewModel.confirmPasswordText,
            onValueChange = { value ->
                viewModel.setConfirmPasswordValue(value)
            },
            placeholder = {
                Text(
                    text = "Confirm Password",
                    style = TextStyle(fontSize = 12.sp, color = Color.Gray)
                )
            },
            shape = RoundedCornerShape(12.dp),


            )

        DrawingCheckBox(modifier)
        DrawingTheSignUpBtn(modifier , navController)
        DrawingSignUpWays()

        Text(
            text = buildAnnotatedString {
                withStyle( SpanStyle(
                    fontSize = 12.sp,
                    color = Color.Gray,
                    fontFamily = fontFamily ,
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append("Already have an account? ")
                }

                withStyle( SpanStyle(
                    fontSize = 12.sp,
                    color = colorResource(R.color.blue),
                    fontFamily = fontFamily ,
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append("Log In")
                }
            } ,
            modifier = Modifier
                .padding(4.dp),
            textAlign = TextAlign.Center
        )

    }
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
    modifier: Modifier
) {
    var isChecked by remember {
        mutableStateOf(true)
    }
    Row(
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = {
                check(it)
            },
            colors = CheckboxDefaults.colors(
                colorResource(R.color.blue)
            )

        )
        Text(
            modifier = modifier.fillMaxWidth().padding(4.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Gray ,
                        fontFamily = fontFamily ,
                    )
                ) {
                    append("I have read, understood & agreed to ToDo’s ")
                }
                withStyle(
                    style = SpanStyle(
                        color = colorResource(R.color.blue),
                        fontFamily = fontFamily ,
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
    modifier: Modifier = Modifier,
    navController: NavController

    ) {
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
            navController.navigate("emailVerification")

        },
        shape = RoundedCornerShape(8.dp)

    ) {
        Text(
            modifier = modifier.padding(8.dp),
            text = "Sign Up",
            fontSize = 16.sp
        )
    }
}


@Composable
fun DrawingSignUpWays() {

    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .fillMaxWidth()
    ) {

        Button(
            onClick = {

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
}

