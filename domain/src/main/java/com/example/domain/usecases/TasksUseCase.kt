package com.example.domain.usecases

import com.example.domain.model.Tasks
import com.example.domain.repository.tasksrepository.TasksRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TasksUseCase @Inject constructor(
    private val tasksRepo :TasksRepository
){

    suspend fun insertTask(
        task: Tasks
    ){
        try {
            tasksRepo.insertTask(task)
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun deleteTask(task:Tasks){
        try {
            tasksRepo.deleteTask(task)
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun updateTask(task:Tasks){
        try {
            tasksRepo.updateTask(task)
        }catch (ex:Exception){
            throw ex
        }
    }

    suspend fun getAllTasks(): Flow<List<Tasks>>{
        try {
            return tasksRepo.getAllTasks()
        }catch (ex:Exception){
            throw ex
        }
    }
}