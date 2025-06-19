package com.example.domain.repository.tasksrepository

import com.example.domain.model.Tasks
import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insertTask(task:Tasks)
    suspend fun deleteTask(task:Tasks)
    suspend fun updateTask(task:Tasks)
    suspend fun getAllTasks(): Flow<List<Tasks>>
}