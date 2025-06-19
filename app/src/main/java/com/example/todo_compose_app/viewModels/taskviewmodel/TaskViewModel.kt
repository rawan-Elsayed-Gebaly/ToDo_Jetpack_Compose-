package com.example.todo_compose_app.viewModels.taskviewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Tasks
import com.example.domain.usecases.TasksUseCase
import com.example.todo_compose_app.screens.task.BottomNavItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val tasksUseCase: TasksUseCase
) : ViewModel() {

    private var _tasks = MutableStateFlow<List<Tasks>>(emptyList())
    val tasks: StateFlow<List<Tasks>> get() = _tasks.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery


    fun onSearchTextChanged(newText: String) {
        _searchQuery.value = newText
    }
    fun insertTask(task: Tasks) {
        viewModelScope.launch {
            tasksUseCase.insertTask(task)
            // Optionally refresh task list if needed (depends on your use case)
        }
    }



    val filteredTasks = searchQuery
        .flatMapLatest { query ->
            if(query.isBlank()){
                tasksUseCase.getAllTasks()
            }else{
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
//init {
//    viewModelScope.launch {
//        tasksUseCase.getAllTasks()
//            .distinctUntilChanged()
//            .collectLatest {
//                    tasksList ->
//                _tasks.value = tasksList
//            }
//    }
//
//}


}