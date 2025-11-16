package com.example.todo_compose_app.screens.bottom_nav_bar.calender


import androidx.activity.ComponentActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todo_compose_app.R
import com.example.todo_compose_app.screens.bottom_nav_bar.task.InFiveDaysTaskList
import com.example.todo_compose_app.viewModels.taskviewmodel.TaskViewModel
import com.example.todo_compose_app.viewModels.taskviewmodel.UiStateViewModel
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.WeekCalendarState
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.core.yearMonth
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId


@Composable
fun CalendarScreen(
    navController: NavController

) {
    CalendarTabs()
}

@Composable
fun MonthCalendarContent(

    taskViewModel: TaskViewModel = hiltViewModel()
) {
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }


    var changeByMonthPicker by remember { mutableStateOf(false) }


    val currentDate = LocalDate.now()
    val startMonth = remember { currentDate.minusMonths(100) }
    val endMonth = remember { currentDate.plusMonths(100) }

    val calendarState = rememberCalendarState(
        startMonth = startMonth.yearMonth,
        firstDayOfWeek = DayOfWeek.SUNDAY,
        endMonth = endMonth.yearMonth,
        firstVisibleMonth = selectedDate?.yearMonth ?: YearMonth.now()
    )

    val startDate = remember { currentDate.minusDays(100) }
    val endDate = remember { currentDate.plusDays(1100) }
    val dayOfWeek = remember { daysOfWeek() }

    val weekCalendarState = rememberWeekCalendarState(
        startDate = startDate,
        endDate = endDate,
        firstDayOfWeek = dayOfWeek.first()
    )

    // ✅ SCROLL the week calendar to selectedDate
    LaunchedEffect(selectedDate) {
        selectedDate?.let {
            weekCalendarState.scrollToWeek(it)
        }
    }


    Box(
        modifier = Modifier.padding(
            14.dp
        )
    ) {

        Column(

        ) {
            Box(
                modifier = Modifier
                    .border(
                        border = BorderStroke(0.2.dp, colorResource(R.color.dark_gray)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
            ) {
                MonthPickerSingleView(
                    selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                        changeByMonthPicker = true
                    }
                )

            }

            Box(
                modifier = Modifier
                    .border(
                        border = BorderStroke(0.2.dp, colorResource(R.color.dark_gray)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
            ) {
                WeekCalendarView(
                    selectedDate,
                    onDateSelected = { date ->
                        selectedDate = date
                    },
                    weekCalendarState

                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier
                    .border(
                        border = BorderStroke(0.2.dp, colorResource(R.color.dark_gray)),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
            ) {
                DayCalendarView(
                    selectedDate,
                    onDateReSelected = { date ->
                        selectedDate = date
                        changeByMonthPicker = false
                    },
                    changeByMonthPicker,
                    calendarState
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                modifier = Modifier.padding( start = 8.dp),
                text = "In 5 Days " ,
                fontSize = 14.sp ,
                fontWeight = FontWeight.SemiBold ,
                color = colorResource(R.color.dark_gray)
            )
            Spacer(modifier = Modifier.height(24.dp))

            InFiveDaysTaskList()
        }
    }
}


@Composable
fun MonthPickerSingleView(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    val monthList = remember {
        val now = YearMonth.now()
        (-120..120).map { now.plusMonths(it.toLong()) }
    }

    val selectedMonth = YearMonth.from(selectedDate)
    val selectedIndex = monthList.indexOfFirst { it == selectedMonth }
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl


    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // ⬅️ Previous
        IconButton(
            onClick = {
                if (selectedIndex > 0) {
                    val newMonth = monthList[selectedIndex - 1]
                    onDateSelected(
                        selectedDate?.withMonth(newMonth.monthValue)
                            ?.withYear(newMonth.year)
                            ?: newMonth.atDay(1)
                    )
                }
            }
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Previous Month")
        }

        // 📆 Selected Month & Year with animation
        AnimatedContent(
            targetState = selectedMonth,
            label = "Month Switch",
            transitionSpec = {
                slideInHorizontally { if (isRtl) -it else it } + fadeIn() togetherWith
                        slideOutHorizontally { if (isRtl) it else -it } + fadeOut()
            }
        ) { yearMonth ->
            val monthName = yearMonth.month.name
                .lowercase()
                .replaceFirstChar { it.uppercase() }

            val year = yearMonth.year

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = monthName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = year.toString(),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }

        // ➡️ Next
        IconButton(
            onClick = {
                if (selectedIndex < monthList.lastIndex) {
                    val newMonth = monthList[selectedIndex + 1]
                    onDateSelected(
                        selectedDate?.withMonth(newMonth.monthValue)
                            ?.withYear(newMonth.year)
                            ?: newMonth.atDay(1)
                    )
                }
            }
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = "Next Month")
        }
    }
}

@Composable
fun DayCalendarView(
    selectedDate: LocalDate?,
    onDateReSelected: (LocalDate) -> Unit,
    forceSelectFirstDayOfMonth: Boolean,
    calendarState: CalendarState

) {

    LaunchedEffect(selectedDate?.yearMonth, forceSelectFirstDayOfMonth) {
        if (forceSelectFirstDayOfMonth && selectedDate != null) {
            val firstDay = selectedDate.yearMonth.atDay(1)
            if (selectedDate != firstDay) {
                onDateReSelected(firstDay)
            }
        }
    }

    HorizontalCalendar(
        state = calendarState,
        dayContent = { day ->
            val date = day.date
            val isSelected = selectedDate == date
            val isCurrent = LocalDate.now() == date
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .background(
                        if (day.date == LocalDate.now()) {
                            colorResource(R.color.dark_blue)
                        } else if (day.date == selectedDate) {
                            colorResource(R.color.light_blue)
                        } else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable {
                        onDateReSelected(day.date)

                    },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    fontSize = 14.sp,
                    color = if (isSelected || isCurrent) Color.White else colorResource(R.color.dark_gray)
                )

            }
        }
    )
}

@Composable
fun WeekCalendarView(
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    weekCalendarState: WeekCalendarState
) {


    WeekCalendar(
        state = weekCalendarState,
        dayContent = { dayState ->
            val date = dayState.date
            val isSelected = selectedDate == date

            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .background(
                        if (isSelected) colorResource(R.color.dark_blue) else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onDateSelected(date) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = date.dayOfWeek.name.take(3).lowercase()
                        .replaceFirstChar { it.uppercase() },
                    color = if (isSelected) Color.White else Color.Black
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTabs(

) {
    val tabsTitles = listOf("Day", "Week", "Month")
    val pagerState = rememberPagerState(initialPage = 0) { tabsTitles.size }
    val coroutineScope = rememberCoroutineScope()


    Box(
        modifier = Modifier.fillMaxWidth(),

        ) {
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
                verticalAlignment = Alignment.Top

            ) { page ->
                when (page) {
                    0 -> DayCalendarContent()
                    1 -> WeekCalendarContent()
                    2 -> MonthCalendarContent()
                }
            }
        }

        CreateTaskWithDateBtn(
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterEnd) ,

        )
    }


}

@Composable
fun CreateTaskWithDateBtn(
    modifier: Modifier,
) {
    val activity = LocalContext.current as ComponentActivity
    val uiStateViewModel: UiStateViewModel = viewModel(activity)
    val coroutineScope = rememberCoroutineScope()


    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(R.color.blue)
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            coroutineScope.launch {
                uiStateViewModel.onOpenBottomSheet()

            }
        },

        ) {

        Icon(
            modifier = Modifier
                .size(20.dp),
            painter = painterResource(R.drawable.add_ic),
            contentDescription = "Add",

            )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            modifier = Modifier.padding(2.dp),
            text = "Add Task",
            fontSize = 12.sp,
            color = Color.White,
        )

    }
}


@Composable
fun DayCalendarContent() {

}

@Composable
fun WeekCalendarContent() {

}

fun LocalDate.toMillisAtStartOfDay(zoneId: ZoneId = ZoneId.systemDefault()): Long {
    return this.atStartOfDay(zoneId).toInstant().toEpochMilli()
}

//@Composable
//fun InFiveDaysTasks(
//    viewModel: TaskViewModel = hiltViewModel()
//) {
//
//    val today = LocalDate.now().toMillisAtStartOfDay()
//    val afterFiveDays = LocalDate.now().plusDays(5).toMillisAtStartOfDay()
//
//
//    val tasksInFiveDays = viewModel.getTasksInFiveDays(today, afterFiveDays)
//        .collectAsState(initial = emptyList())
//
//
//}