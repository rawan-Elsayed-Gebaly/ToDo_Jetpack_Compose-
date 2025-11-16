package com.example.todo_compose_app.viewModels.taskviewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Tasks
import com.example.domain.usecases.TasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase
) : ViewModel() {

    private var _tasksInFiveDays = MutableStateFlow<List<Tasks>>(emptyList())
    val tasksInFiveDays: StateFlow<List<Tasks>> get() = _tasksInFiveDays.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()


    // In ViewModel
    private val _selectedDate = MutableStateFlow<Long?>(System.currentTimeMillis())
    val selectedDate: StateFlow<Long?> get() = _selectedDate

    fun setSelectedDate(dateInMillis: Long) {
        _selectedDate.value = dateInMillis
    }




    fun onSearchTextChanged(newText: String) {
        _searchQuery.value = newText
        Log.d("tag" , "${_searchQuery.value} _searchQuery")
        Log.d("tag" , "${searchQuery.value} searchQuery")
    }
    fun insertTask(task: Tasks) {
        viewModelScope.launch {
            tasksUseCase.insertTask(task)
            // Optionally refresh task list if needed (depends on your use case)
        }
    }




    val filteredTasks = searchQuery
        .flatMapLatest { query ->
            Log.d("tag" , "$query viewModel")
            if(query.isBlank()){
                tasksUseCase.getAllTasks()
            }else{
                Log.d("tag" , "$query viewModel query")
                tasksUseCase.searchTasks(query)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


    fun updateTask(task: Tasks) {
        viewModelScope.launch(Dispatchers.IO) {
            tasksUseCase.updateTask(task)
        }
    }


    suspend fun deleteAllTasks() {
        tasksUseCase.deleteAllTasks()
    }

    suspend fun deleteTask(task:Tasks){
        tasksUseCase.deleteTask(task)
    }

    fun getTasksInFiveDays(today:Long , afterFiveDays:Long): Flow<List<Tasks>> {
       return tasksUseCase.getTasksInFiveDays(today , afterFiveDays)
    }


}