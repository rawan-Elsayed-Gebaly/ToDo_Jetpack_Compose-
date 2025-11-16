package com.example.todo_compose_app.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.todo_compose_app.screens.authentication.login.LogInScreen
import com.example.todo_compose_app.screens.authentication.signup.EmailVerificationScreen
import com.example.todo_compose_app.screens.authentication.signup.GetStaredScreen
import com.example.todo_compose_app.screens.authentication.signup.SignUpScreen
import com.example.todo_compose_app.screens.authentication.signup.SuccessFullCreatedScreen
import com.example.todo_compose_app.screens.bottom_nav_bar.calender.CalendarScreen
import com.example.todo_compose_app.screens.bottom_nav_bar.menu.MenuScreen
import com.example.todo_compose_app.screens.bottom_nav_bar.profile.ProfileScreen
import com.example.todo_compose_app.screens.bottom_nav_bar.task.TaskScreen


@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController , startDestination = "calendar") {
        composable("getStart") { GetStaredScreen((navController)) }
        composable("signUp") { SignUpScreen(navController) }
        composable("emailVerification") { EmailVerificationScreen(navController) }
        composable("successfullyCreated") { SuccessFullCreatedScreen(navController) }
        composable("login") { LogInScreen(navController) }
        composable("task") { TaskScreen(navController) }
        composable("calendar") { CalendarScreen(navController) }
        composable("profile") { ProfileScreen(navController) }
        composable("menu") { MenuScreen(navController) }


    }
}