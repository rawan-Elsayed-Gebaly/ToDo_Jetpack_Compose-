package com.example.todo_compose_app.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo_compose_app.screens.signup.EmailVerificationScreen
import com.example.todo_compose_app.screens.signup.GetStaredScreen
import com.example.todo_compose_app.screens.signup.SignUpScreen
import com.example.todo_compose_app.screens.signup.SuccessFullCreatedScreen


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController , startDestination = "getStart") {
        composable("getStart") { GetStaredScreen((navController)) }
        composable("signUp") { SignUpScreen(navController) }
        composable("emailVerification") { EmailVerificationScreen(navController) }
        composable("successfullyCreated") { SuccessFullCreatedScreen(navController) }

    }
}