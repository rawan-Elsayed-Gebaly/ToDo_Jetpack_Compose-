package com.example.data.datasource.localdatasource

import com.example.data.local.database.tasksdb.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TasksDataSource {

    suspend fun insertTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
    suspend fun updateTask(taskEntity: TaskEntity)
    suspend fun getAllTasks(): Flow<List<TaskEntity>>

}