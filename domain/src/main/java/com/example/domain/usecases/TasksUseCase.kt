package com.example.domain.usecases

import com.example.domain.model.Tasks
import com.example.domain.repository.tasksrepository.TasksRepository
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class TasksUseCase @Inject constructor(
    private val tasksRepo :TasksRepository
){

    suspend fun insertTask(
        task: Tasks
    )  = withContext(Dispatchers.IO){
        try {
            tasksRepo.insertTask(task)
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun deleteTask(task:Tasks)  = withContext(Dispatchers.IO){
        try {
            tasksRepo.deleteTask(task)
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun updateTask(task:Tasks) = withContext(Dispatchers.IO) {
        try {
            tasksRepo.updateTask(task)
        }catch (ex:Exception){
            throw ex
        }
    }

     fun getAllTasks(): Flow<List<Tasks>>{
        try {
            return tasksRepo.getAllTasks()
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun deleteAllTasks()  = withContext(Dispatchers.IO){
        tasksRepo.deleteAllTasks()
    }

    fun searchTasks(searchQuery:String):Flow<List<Tasks>>{
        return tasksRepo.searchTasks(searchQuery)
    }
}