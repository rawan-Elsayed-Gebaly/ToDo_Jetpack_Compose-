package com.example.todo_compose_app.screens.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Tasks
import com.example.todo_compose_app.R
import com.example.todo_compose_app.screens.Schedule
import com.example.todo_compose_app.screens.Completed
import com.example.todo_compose_app.screens.Today
import com.example.todo_compose_app.viewModels.taskviewmodel.TaskViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun TaskScreen(
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val taskViewModel: TaskViewModel = hiltViewModel()
    TaskScreenContent(taskViewModel)
}


@Composable
fun TaskScreenContent(
    tasksViewModel: TaskViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(vertical = 35.dp, horizontal = 12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            DrawingSearchBar(
                tasksViewModel
            )
            Spacer(Modifier.height(8.dp))
            DrawingTabs()
        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingSearchBar(
    viewModel: TaskViewModel
) {
    val searchQuery by viewModel.searchQuery.collectAsState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo + Title (No weight here!)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            DrawingToDoTheIconTaskScreen()
            Text(
                modifier = Modifier.padding(start = 8.dp), text = "To Do", style = TextStyle(
                    fontSize = 12.sp,
                    color = colorResource(R.color.blue),
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.width(55.dp)) // Pushes search bar to the end

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { newText ->
                viewModel.onSearchTextChanged(newText)
            },
            placeholder = { Text("Search") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(56.dp) // match Material spec
                .background(Color.White),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,

            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = colorResource(R.color.black),
                unfocusedIndicatorColor = colorResource(R.color.gray),
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )

        )
    }

}

@Composable
fun DrawingToDoTheIconTaskScreen(modifier: Modifier = Modifier) {
    Icon(
        modifier = modifier.size(30.dp),
        painter = painterResource(R.drawable.ic_to_do),
        contentDescription = "",
        tint = Color.Unspecified

    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTabs(
) {
    val tabsTitles = listOf("Schedule", "Today", "Completed")
    val pagerState = rememberPagerState(initialPage = 0) { tabsTitles.size }
    val coroutineScope = rememberCoroutineScope()

    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            edgePadding = 0.dp,
            indicator = { tabPositions ->
                // Custom underline color for selected tab
                Box(
                    Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage]) // Align indicator with selected tab
                        .height(4.dp) // Set the height of the underline
                        .background(colorResource(R.color.blue)) // Change this to your desired color
                )
            }
        ) {
            tabsTitles.forEachIndexed { index, title ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)

                        }
                    },

                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
//                                color = colorResource(R.color.blue)
                            )
                        )
                    },
                    selectedContentColor = colorResource(R.color.blue),
                    unselectedContentColor = colorResource(R.color.gray),
                    modifier = Modifier.background(color = Color.Transparent),

                    )
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.Top

        ) { page ->
            when (page) {
                0 -> Schedule()
                1 -> Today()
                2 -> Completed()
            }
        }

    }
}


@Composable
fun DrawingCreateTaskBottomSheet(
    tasksViewModel: TaskViewModel,
    onTaskCreated: () -> Unit
) {


    var taskTitle = remember { mutableStateOf("") }
    var taskDescription = remember { mutableStateOf("") }
    val currentTime = System.currentTimeMillis()

    var taskDate = remember { mutableStateOf<Long?>(currentTime) }
    val showTitleError = remember { mutableStateOf(false) }

    var taskPriority = remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), // ⬅️ Only take needed height
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp) // 🧠 Adds spacing between items
    ) {

        Text(
            text = "Create Task",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Row(modifier = Modifier) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                DrawingTaskTitleTextFiled(
                    taskTitle,
                    showError = showTitleError.value
                )
            }

            Spacer(modifier = Modifier.width(8.dp)) // Optional space between them

            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
            ) {

                DrawingDateEditText(taskDate)
            }
        }

        DrawingTaskDescriptionTextFiled(
            taskDescription
        )


//        Row(modifier = Modifier) {
//
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) {
//               // DrawingStartTimeFiled()
//            }
//            Spacer(modifier = Modifier.width(8.dp))
//            Box(
//                modifier = Modifier
//                    .weight(1f)
//                    .fillMaxWidth()
//            ) {
//               // DrawingEndTimeFiled()
//            }
//
//        }

        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                DrawingReminder()

            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                //DrawingRepeat()
                DrawingDropDownMenu(
                    taskPriority
                )


            }
        }

//        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth(0.8f)

        ) {
            DrawingCreateTaskBtn(
                tasksViewModel,
                taskTitle,
                taskDate,
                taskDescription,
                onTaskCreated,
                showTitleError,
                taskPriority
            )


        }


        Spacer(modifier = Modifier.width(8.dp))


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingCreateTaskBtn(
    tasksViewModel: TaskViewModel,
    taskTitle: MutableState<String>,
    taskDate: MutableState<Long?>,
    taskDescription: MutableState<String>,
    onTaskCreated: () -> Unit,
    showTitleError: MutableState<Boolean>,
    taskPriority:MutableState<String>
) {


    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 22.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue)
        ),
        shape = RoundedCornerShape(8.dp),
        onClick = {

            // check if the title is not null
            if (taskTitle.value.isBlank()) {
                showTitleError.value = true

            } else {
                showTitleError.value = false
                onTaskCreated() // call parent to hide sheet
            }

           Log.d("P" , "$taskPriority taskCreated")
            val newTask = Tasks(
                title = taskTitle.value,
                description = taskDescription.value,
                date = taskDate.value,
                isDone = false,
                priority = taskPriority.value
            )
            tasksViewModel.insertTask(newTask)

        }
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Create Task",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun DrawingDateEditText(
    taskDate: MutableState<Long?>
) {
    var onChangeDateValue by remember { mutableStateOf("") }
    var timeInMillis: Long? = null
    timeInMillis = taskDate.value
    if (timeInMillis.toString() == "null") {
        onChangeDateValue = ""
    } else {
        onChangeDateValue = dateFormater(timeInMillis!!)
    }

    var showDatePicker by remember { mutableStateOf(false) }

    // Show the actual date picker if needed
    if (showDatePicker) {
        ShowDateTimePicker(
            onDateTimeSelected = { pickedDate ->
                taskDate.value = pickedDate
                showDatePicker = false
            },
            onDismissRequest = {
                showDatePicker = false
            })
    }


    OutlinedTextField(
        value = onChangeDateValue ?: "",
        onValueChange = {
        },
        label = {
            Text(
                text = "Date",
                fontSize = 14.sp,
                color = colorResource(R.color.createTaskTxtColor),
                fontWeight = FontWeight.Bold
            )
        },
        readOnly = true, // important!
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = "Calendar Icon",
                modifier = Modifier
                    .clickable {
                        showDatePicker = true
                    }
            )
        },
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // datePickerDialog.show()
            },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedLabelColor = colorResource(R.color.createTaskBorderColor),
            focusedContainerColor = Color.Transparent,
            unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.createTaskBorderColor),
            unfocusedIndicatorColor = colorResource(R.color.createTaskBorderColor)

        ),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )

}

@Composable
fun ShowDateTimePicker(
    context: Context = LocalContext.current,
    onDateTimeSelected: (Long) -> Unit, // Return timestamp in millis
    onDismissRequest: () -> Unit
) {
    val calendar = remember { Calendar.getInstance() }

    val timePickerDialog = remember {
        TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                onDateTimeSelected(calendar.timeInMillis) // ✅ Full timestamp
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        ).apply {
            setOnCancelListener {
                onDismissRequest()
            }
            setOnDismissListener {
                onDismissRequest()
            }
        }
    }

    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                // ✅ After date is picked, show time picker
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).apply {
            setOnCancelListener {
                onDismissRequest()
            }
            setOnDismissListener {
                onDismissRequest()
            }
        }
    }

    // Trigger the Date Picker (you can move this trigger to a button)
    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}

fun dateFormater(
    selectedDateTimeInMillis: Long
): String {
    val formatter = SimpleDateFormat("d MMM. h:mm a", Locale.getDefault())
    return formatter.format(Date(selectedDateTimeInMillis))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTaskTitleTextFiled(
    taskTitle: MutableState<String>,
    showError: Boolean
) {


    OutlinedTextField(
        value = taskTitle.value,
        onValueChange = {
            taskTitle.value = it
        },
        label = {
            Text(
                text = "Task title",
                fontSize = 14.sp,
                color = colorResource(R.color.createTaskTxtColor),
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier // 👈 Needed for positioning
            .fillMaxWidth(),
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedLabelColor = colorResource(R.color.createTaskBorderColor),
            focusedContainerColor = Color.Transparent,
            unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.createTaskBorderColor),
            unfocusedIndicatorColor = colorResource(R.color.createTaskBorderColor)


        ),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp
        ),
        maxLines = 1,
        singleLine = true,
        isError = showError,
        supportingText = {
            if (showError) {
                Text(
                    text = "Title can't be empty",
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingTaskDescriptionTextFiled(
    taskDescription: MutableState<String>,
) {
    OutlinedTextField(
        value = taskDescription.value,
        onValueChange = {
            taskDescription.value = it
        },
        label = {
            Text(
                text = "Note",
                fontSize = 14.sp,
                color = colorResource(R.color.createTaskTxtColor),
                fontWeight = FontWeight.Bold
            )
        },
        modifier = Modifier // 👈 Needed for positioning
            .fillMaxWidth(),
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedLabelColor = colorResource(R.color.createTaskBorderColor),
            focusedContainerColor = Color.Transparent,
            unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = colorResource(R.color.createTaskBorderColor),
            unfocusedIndicatorColor = colorResource(R.color.createTaskBorderColor)


        ),
        shape = RoundedCornerShape(
            topStart = 16.dp,
            topEnd = 16.dp,
            bottomStart = 16.dp
        ),
        maxLines = 5,
        minLines = 2,
        singleLine = false,
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingDropDownMenu(
    taskPriority:MutableState<String>
) {

    val priorityOptions = listOf("High", "Medium", "Low")
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
            .background(color = Color.White)

    ) {
        OutlinedTextField(
            value = taskPriority.value,
            onValueChange = {
                taskPriority.value = it
            },
            label = {
                Text(
                    text = "Priority",
                    fontSize = 14.sp,
                    color = colorResource(R.color.createTaskTxtColor),
                    fontWeight = FontWeight.Bold
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            readOnly = true,
            modifier = Modifier
                .menuAnchor() // 👈 Needed for positioning
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedLabelColor = colorResource(R.color.createTaskBorderColor),
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.createTaskBorderColor),
                unfocusedIndicatorColor = colorResource(R.color.createTaskBorderColor)

            ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp
            )
        )

        //Drop Down items
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            priorityOptions.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        taskPriority.value = item
                        expanded = false
                        Log.d("P" ,"$item DropDownMenu")
                    },
                    modifier = Modifier
                        .background(Color.White)

                )

            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingReminder() {

    val reminderOptions = listOf("High", "Medium", "Low")
    var selectedItemText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }


    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        },
        modifier = Modifier
            .background(color = Color.White)
    ) {

        OutlinedTextField(
            value = selectedItemText,
            onValueChange = { selectedItemText = it },
            label = {
                Text(
                    text = "Reminder",
                    fontSize = 14.sp,
                    color = colorResource(R.color.createTaskTxtColor),
                    fontWeight = FontWeight.Bold
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            readOnly = true,
            modifier = Modifier
                .menuAnchor() // 👈 Needed for positioning
                .fillMaxWidth(),
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedLabelColor = colorResource(R.color.createTaskBorderColor),
                focusedContainerColor = Color.Transparent,
                unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = colorResource(R.color.createTaskBorderColor),
                unfocusedIndicatorColor = colorResource(R.color.createTaskBorderColor)
            ),
            shape = RoundedCornerShape(
                topStart = 16.dp,
                topEnd = 16.dp,
                bottomEnd = 16.dp
            )
        )

        //Drop Down items

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            reminderOptions.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        selectedItemText = item
                        expanded = false
                    },
                    modifier = Modifier
                        .background(Color.White)

                )

            }
        }

    }


}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingUpdateTaskBottomSheet(
    tasksViewModel: TaskViewModel,
    task: Tasks,
    onTaskUpdated: () -> Unit

) {

    var taskTitle = remember { mutableStateOf(task.title) }
    var taskDate = remember { mutableStateOf<Long?>(task.date!!) }
    var taskDescription = remember { mutableStateOf(task.description!!) }
    val showTitleError = remember { mutableStateOf(false) }
    var taskPriority = remember { mutableStateOf(task.priority!!) }


    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(), // ⬅️ Only take needed height
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp) // 🧠 Adds spacing between items
    ) {

        Text(
            text = "Update Task",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Row(modifier = Modifier) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                // DrawingDropDownMenu()
                DrawingTaskTitleTextFiled(taskTitle, showTitleError.value)
            }

            Spacer(modifier = Modifier.width(8.dp)) // Optional space between them


            Box(
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
            ) {
                DrawingDateEditText(taskDate)
            }
        }

        DrawingTaskDescriptionTextFiled(
            taskDescription
        )

        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                DrawingReminder()

            }
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                DrawingDropDownMenu(
                    taskPriority
                )

            }
        }

//        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier.fillMaxWidth(0.8f)

        ) {
            DrawingUpdateTaskBtn(
                tasksViewModel,
                task.copy(
                    title = taskTitle.value,
                    date = taskDate.value,
                    description = taskDescription.value ,
                    priority = taskPriority.value
                ),
                onTaskUpdated,
                showTitleError
            )


        }


        Spacer(modifier = Modifier.width(8.dp))


    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawingUpdateTaskBtn(
    tasksViewModel: TaskViewModel,
    task: Tasks,
    onTaskUpdated: () -> Unit,
    showTitleError: MutableState<Boolean>

) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 22.dp
            ),

        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue) // Set the background color to blue
        ),
        onClick = {

            Log.d("log", "clicked")

            // check if the title is not null
            if (task.title.isBlank()) {
                showTitleError.value = true

            } else {
                showTitleError.value = false
                onTaskUpdated()
            }


            tasksViewModel.updateTask(task)


        },
        shape = RoundedCornerShape(8.dp),
    ) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Update Task",
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.SemiBold
        )

    }
}


//fun DrawingStartTimeFiled() {
//
//    var selectedStartTime by remember { mutableStateOf("") }
//
//    OutlinedTextField(
//        value = selectedStartTime,
//        onValueChange = {},
//        label = {
//            Text(
//                text = "Start Time",
//                fontSize = 14.sp,
//                color = colorResource(R.color.createTaskTxtColor),
//                fontWeight = FontWeight.Bold
//            )
//        },
//        readOnly = true, // important!
//        trailingIcon = {
//            Icon(
//                imageVector = Icons.Default.DateRange,
//                contentDescription = "Calendar Icon",
//                modifier = Modifier.clickable {
//                    //  datePickerDialog.show()
//                }
//            )
//        },
//        maxLines = 1,
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                // datePickerDialog.show()
//            },
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedLabelColor = colorResource(R.color.createTaskBorderColor),
//            focusedContainerColor = Color.Transparent,
//            unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
//            unfocusedContainerColor = Color.Transparent,
//            focusedBorderColor = colorResource(R.color.createTaskBorderColor),
//            unfocusedBorderColor = colorResource(R.color.createTaskBorderColor)
//        ),
//        shape = RoundedCornerShape(
//            topStart = 16.dp,
//            topEnd = 16.dp,
//            bottomEnd = 16.dp
//        )
//
//    )
//
//
//}


//@Composable
//fun DrawingEndTimeFiled() {
//
//    var selectedEndTime by remember { mutableStateOf("") }
//
//    OutlinedTextField(
//        value = selectedEndTime,
//        onValueChange = {},
//        label = {
//            Text(
//                text = "End Time",
//                fontSize = 14.sp,
//                color = colorResource(R.color.createTaskTxtColor),
//                fontWeight = FontWeight.Bold
//            )
//        },
//
//        readOnly = true, // important!
//        trailingIcon = {
//            Icon(
//                imageVector = Icons.Default.DateRange,
//                contentDescription = "Calendar Icon",
//                modifier = Modifier.clickable {
//                    //  datePickerDialog.show()
//                }
//            )
//        },
//        maxLines = 1,
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                // datePickerDialog.show()
//            },
//
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedLabelColor = colorResource(R.color.createTaskBorderColor),
//            focusedContainerColor = Color.Transparent,
//            unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
//            unfocusedContainerColor = Color.Transparent,
//            focusedBorderColor = colorResource(R.color.createTaskBorderColor),
//            unfocusedBorderColor = colorResource(R.color.createTaskBorderColor)
//        ),
//        shape = RoundedCornerShape(
//            topStart = 16.dp,
//            topEnd = 16.dp,
//            bottomStart = 16.dp
//        )
//    )
//
//
//}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DrawingRepeat() {
//
//    val repeatOptions = listOf("High", "Medium", "Low")
//    var selectedItemText by remember { mutableStateOf("") }
//    var expanded by remember { mutableStateOf(false) }
//
//
//    ExposedDropdownMenuBox(
//        expanded = expanded,
//        onExpandedChange = {
//            expanded = !expanded
//        },
//        modifier = Modifier
//            .background(color = Color.White)
//    ) {
//
//        OutlinedTextField(
//            value = selectedItemText,
//            onValueChange = { selectedItemText = it },
//            label = {
//                Text(
//                    text = "Repeat",
//                    fontSize = 14.sp,
//                    color = colorResource(R.color.createTaskTxtColor),
//                    fontWeight = FontWeight.Bold
//                )
//            },
//            trailingIcon = {
//                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//            },
//            readOnly = true,
//            modifier = Modifier
//                .menuAnchor() // 👈 Needed for positioning
//                .fillMaxWidth(),
//            colors = ExposedDropdownMenuDefaults.textFieldColors(
//                focusedLabelColor = colorResource(R.color.createTaskBorderColor),
//                focusedContainerColor = Color.Transparent,
//                unfocusedLabelColor = colorResource(R.color.createTaskBorderColor),
//                unfocusedContainerColor = Color.Transparent,
//                focusedIndicatorColor = colorResource(R.color.createTaskBorderColor),
//                unfocusedIndicatorColor = colorResource(R.color.createTaskBorderColor)
//            ),
//            shape = RoundedCornerShape(
//                topStart = 16.dp,
//                topEnd = 16.dp,
//                bottomStart = 16.dp
//            )
//        )
//
//        //Drop Down items
//
//        ExposedDropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false }
//        ) {
//
//            repeatOptions.forEach { item ->
//                DropdownMenuItem(
//                    text = { Text(item) },
//                    onClick = {
//                        selectedItemText = item
//                        expanded = false
//                    },
//                    modifier = Modifier
//                        .background(Color.White)
//
//                )
//
//            }
//        }
//
//    }
//
//
//}
