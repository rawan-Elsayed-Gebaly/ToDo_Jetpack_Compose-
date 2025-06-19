package com.example.data.datasource.localdatasource

import com.example.data.local.database.tasksdb.TaskEntity
import com.example.domain.model.Tasks
import kotlinx.coroutines.flow.Flow

interface TasksDataSource {

    suspend fun insertTask(taskEntity: TaskEntity)
    suspend fun deleteTask(taskEntity: TaskEntity)
    suspend fun updateTask(taskEntity: TaskEntity)
     fun getAllTasks(): Flow<List<TaskEntity>>
    suspend fun deleteAllTasks()
    fun searchTasks (searchQuery:String):Flow<List<TaskEntity>>

}