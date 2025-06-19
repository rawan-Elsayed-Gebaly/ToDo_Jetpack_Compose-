package com.example.todo_compose_app.screens.task

import com.example.todo_compose_app.R

sealed class BottomNavItem(
    val route:String,
    val title:String,
    val icon: Int

) {

    data object Menu :BottomNavItem("menu" , "Menu" , R.drawable.menu_ic)
    data object Task:BottomNavItem("task" , "Task" , R.drawable.task_square_ic)
    data object Calendar:BottomNavItem("calendar" , "Calendar" , R.drawable.calendar_ic)
    data object Profile:BottomNavItem("profile" , "Profile" , R.drawable.profile_circle_ic)

}