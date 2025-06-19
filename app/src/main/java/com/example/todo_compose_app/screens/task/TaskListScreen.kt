package com.example.todo_compose_app.screens.task

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.domain.model.Tasks
import com.example.todo_compose_app.R
import com.example.todo_compose_app.viewModels.taskviewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.util.Calendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayTaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val tasks by viewModel.filteredTasks.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    // Bottom Sheet state (shared)
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Tasks?>(null) }


    val todayStart = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
    val todayEnd = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    }

    // Filter upcoming tasks
    val todayTasks by remember(tasks) {
        derivedStateOf {
            tasks.filter { task ->
                val taskDate = task.date
                taskDate != null &&
                        taskDate in todayStart..todayEnd &&
                        !task.isDone
            }
        }
    }

    if (todayTasks.isEmpty()) {
        ShowEmptyPage(stringResource(R.string.empty_Today_Tasks_page_txt))
        return
    }


    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            showBottomSheet = false
            selectedTask = null
        }
    }

    // ✅ Task list
    LazyColumn {
        items(todayTasks, key = { it.id }) { task ->


            val taskDate = task.date?.let { dateFormater(it) } ?: "No Date"
            val taskPriority = task.priority
            val taskFlag: Int? = checkTaskPriority(task)
            Log.d("P", "$taskPriority item list ")
            DrawingTaskItem(
                task = task ,
                taskTitle = task.title.orEmpty(),
                taskDate = taskDate,
                isDone = task.isDone,
                onCheckedChange = { changedValue ->
                    coroutineScope.launch {
                        viewModel.updateTask(task.copy(isDone = changedValue))
                    }
                },
                dateColor = R.color.semi_transparent_dark_blue,
                onClick = {
                    selectedTask = task
                    showBottomSheet = true
                    coroutineScope.launch {
                        sheetState.show()
                    }
                },
                onRemove = {
                    coroutineScope.launch {
                        viewModel.deleteTask(it)
                    }
                },
                flag = taskFlag ?: 0
            )
        }
    }

    // ✅ Shared Bottom Sheet
    if (showBottomSheet && selectedTask != null) {
        ModalBottomSheet(
            onDismissRequest = {
                hideBottomSheet()
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = colorResource(R.color.white)
        ) {
            DrawingUpdateTaskBottomSheet(
                tasksViewModel = viewModel,
                task = selectedTask!!,
                onTaskUpdated = {
                    hideBottomSheet()
                }
            )
        }
    }
}

fun checkTaskPriority(task: Tasks): Int? {
    Log.d("P", "${task.priority} check Priority")

    val priorityOptions = listOf("High", "Medium", "Low")
    return when (task.priority) {
        priorityOptions[0] -> R.drawable.red_flag
        priorityOptions[1] -> R.drawable.green_flag
        priorityOptions[2] -> R.drawable.yellow_flag
        else -> {
            return null
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTaskListScreen(
    viewModel: TaskViewModel = hiltViewModel(),
) {
    val tasks by viewModel.filteredTasks.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    // Shared Bottom Sheet states
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Tasks?>(null) }


    val todayStart = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }.timeInMillis
    }
    val todayEnd = remember {
        Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }.timeInMillis
    }

    // Filter past due tasks
    val scheduleTasks by remember(tasks) {
        derivedStateOf {
            tasks.filter {
                it.date != null &&
                        (it.date!! < todayStart || it.date!! > todayEnd) &&
                        !it.isDone
            }
        }
    }

    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            showBottomSheet = false
            selectedTask = null
        }
    }
    if (scheduleTasks.isEmpty()) {
        ShowEmptyPage(stringResource(R.string.empty_behind_schedule_page_txt))
        return
    }


    LazyColumn {
        items(scheduleTasks, key = { it.id }) { task ->

            val taskDate = task.date?.let { dateFormater(it) } ?: "No Date"
            val taskColor: Int = if (task.date!! < todayStart) {
                R.color.red
            } else {
                R.color.semi_transparent_dark_blue
            }
            val taskFlag: Int? = checkTaskPriority(task)


            DrawingTaskItem(
                task = task ,
                taskTitle = task.title.orEmpty(),
                taskDate = taskDate,
                isDone = task.isDone,
                onCheckedChange = { changedValue ->
                    coroutineScope.launch {
                        viewModel.updateTask(task.copy(isDone = changedValue))
                    }
                },
                dateColor = taskColor,
                onClick = {
                    selectedTask = task
                    showBottomSheet = true
                    coroutineScope.launch { sheetState.show() }
                },
                onRemove = {
                    coroutineScope.launch {
                        viewModel.deleteTask(it)
                    }
                },
                flag = taskFlag ?: 0
            )
        }
    }

    // Shared Bottom Sheet
    if (showBottomSheet && selectedTask != null) {
        ModalBottomSheet(
            onDismissRequest = {
                hideBottomSheet()
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = colorResource(R.color.white)
        ) {
            DrawingUpdateTaskBottomSheet(
                tasksViewModel = viewModel,
                task = selectedTask!!,
                onTaskUpdated = {
                    hideBottomSheet()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTaskListScreen(viewModel: TaskViewModel = hiltViewModel()) {

    val tasks by viewModel.filteredTasks.collectAsStateWithLifecycle()
    val coroutineScope = rememberCoroutineScope()

    // Shared Bottom Sheet states
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }
    var selectedTask by remember { mutableStateOf<Tasks?>(null) }


    // Filter tasks where the date is not null and is before now
    val doneTasks by remember(tasks) {
        derivedStateOf {
            tasks.filter { it.isDone }
        }
    }

    if (doneTasks.isEmpty()) {
        ShowEmptyPage(stringResource(R.string.empty_completed_page_txt))
        return
    }

    fun hideBottomSheet() {
        coroutineScope.launch {
            sheetState.hide()
        }.invokeOnCompletion {
            showBottomSheet = false
            selectedTask = null
        }
    }

    LazyColumn {
        items(doneTasks, key = { it.id }) { task ->
            val taskDate = task.date?.let { dateFormater(it) } ?: "Null"
            val taskFlag: Int? = checkTaskPriority(task)


            DrawingTaskItem(
                task = task ,
                taskTitle = task.title!!,
                taskDate = taskDate,
                isDone = task.isDone,
                onCheckedChange = { changedValue ->
                    coroutineScope.launch {
                        viewModel.updateTask(
                            task.copy(
                                isDone = changedValue
                            )
                        )
                    }
                },
                R.color.semi_transparent_dark_blue,
                onClick = {

                    selectedTask = task
                    showBottomSheet = true
                    coroutineScope.launch { sheetState.show() }

                },
                onRemove = {
                    coroutineScope.launch {
                        viewModel.deleteTask(it)
                    }
                },
                flag = taskFlag ?: 0


            )
        }
    }

    // Shared Bottom Sheet
    if (showBottomSheet && selectedTask != null) {
        ModalBottomSheet(
            onDismissRequest = {
                hideBottomSheet()
            },
            sheetState = sheetState,
            shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            containerColor = colorResource(R.color.white)
        ) {
            DrawingUpdateTaskBottomSheet(
                tasksViewModel = viewModel,
                task = selectedTask!!,
                onTaskUpdated = {
                    hideBottomSheet()
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTaskItem(
    task: Tasks ,
    taskTitle: String,
    taskDate: String,
    isDone: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    dateColor: Int,
    onClick: () -> Unit,
    onRemove :(Tasks)->Unit ,
    flag: Int
) {

    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onRemove(task)
                true // Allow the item to be dismissed
            } else {
                false // Don't dismiss on other directions
            }
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        modifier = Modifier.fillMaxSize(),
        backgroundContent = {
            if (swipeToDismissBoxState.dismissDirection
                == SwipeToDismissBoxValue.EndToStart ) {


                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remove item",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                            .wrapContentSize(Alignment.CenterEnd)
                            .padding(12.dp),
                        tint = Color.White
                    )

            }
        },
    ){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(0.6.dp, color = colorResource(R.color.createTaskBorderColor)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = isDone,
                    onCheckedChange = onCheckedChange,
                    modifier = Modifier.padding(end = 8.dp),
                    colors = CheckboxDefaults.colors(checkedColor = colorResource(R.color.blue))
                )

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(

                        text = taskTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = taskDate,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = colorResource(id = dateColor)
                    )
                }

                if (flag != 0) {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .padding(2.dp),
                        painter = painterResource(id = flag),
                        tint = Color.Unspecified, // 👈 Keeps original vector color
                        contentDescription = "Priority  Icon"
                    )
                }

            }
        }
    }


}


@Composable
fun ShowEmptyPage(
    text: String
) {
    Box(
        modifier = Modifier.fillMaxSize(), // fill the entire screen
        contentAlignment = Alignment.Center // center child both vertically and horizontally
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = colorResource(R.color.createTaskTxtColor),
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )
    }
}
