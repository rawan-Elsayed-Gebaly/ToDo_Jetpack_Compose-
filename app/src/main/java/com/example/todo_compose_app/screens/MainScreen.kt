package com.example.todo_compose_app.screens

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.todo_compose_app.R
import com.example.todo_compose_app.screens.task.ScheduleTaskListScreen
import com.example.todo_compose_app.screens.task.BottomNavItem
import com.example.todo_compose_app.screens.task.CompletedTaskListScreen
import com.example.todo_compose_app.screens.task.DrawingCreateTaskBottomSheet
import com.example.todo_compose_app.screens.task.TodayTaskListScreen
import com.example.todo_compose_app.viewModels.taskviewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    showBottomSheet: Boolean,
    sheetState: SheetState,
    onDismissBottomSheet: () -> Unit,
    taskViewModel: TaskViewModel


) {


    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissBottomSheet,
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = colorResource(R.color.white),
        ) {
            DrawingCreateTaskBottomSheet(
                taskViewModel,
                onTaskCreated = onDismissBottomSheet
            )
        }
    }
}


@Composable
fun BottomNavBar(
    navHostController: NavHostController
) {
    val items = listOf(
        BottomNavItem.Menu,
        BottomNavItem.Task,
        BottomNavItem.Calendar,
        BottomNavItem.Profile,
    )

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = colorResource(R.color.white)
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.title,
                        modifier = Modifier.size(25.dp)
                    )
                },
                label = { Text(item.title, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
                selected = currentRoute == item.route,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent, // 🚫 Removes default selected background
                    selectedIconColor = colorResource(R.color.dark_blue),
                    selectedTextColor = colorResource(R.color.blue)
                ),
                onClick = {
                    if (currentRoute != item.route) {
                        navHostController.navigate(item.route) {
                            // Pop up to the start destination to avoid building up a large back stack
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            // Avoid multiple copies of the same destination
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}


@Composable
fun DrawingAddBtn(
    onClick: () -> Unit
) {

    FloatingActionButton(
        onClick = {
            onClick()
            Log.d("tag", "FABClicked")
        },
        containerColor = colorResource(
            R.color.blue
        ),
        contentColor = colorResource(R.color.white),
        shape = RoundedCornerShape(56.dp)
    ) {

        Icon(
            Icons.Filled.Add,
            contentDescription = "add task"
        )

    }


}


@Composable
fun Schedule() {
    ScheduleTaskListScreen()
}

@Composable
fun Today(
) {
    TodayTaskListScreen()
}
@Composable
fun Completed() {
    CompletedTaskListScreen()
}
