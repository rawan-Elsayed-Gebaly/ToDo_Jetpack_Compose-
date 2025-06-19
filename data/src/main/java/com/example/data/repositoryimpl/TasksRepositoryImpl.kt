package com.example.data.repositoryimpl

import com.example.data.datasource.localdatasource.TasksDataSource
import com.example.data.local.database.tasksdb.TaskEntity
import com.example.data.local.database.tasksdb.toDomain
import com.example.domain.model.Tasks
import com.example.domain.repository.tasksrepository.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksRepositoryImpl @Inject constructor(
    private val tasksDataSource:TasksDataSource
):TasksRepository {

    override suspend fun insertTask(task: Tasks) {

        tasksDataSource.insertTask(TaskEntity(
            id = task.id,
            title = task.title,
            description = task.description,
            date =  task.date,
            isDone = task.isDone,
            priority = task.priority

        ))
    }
    override suspend fun deleteTask(task: Tasks) {
        tasksDataSource.deleteTask(
            TaskEntity(
                id = task.id,
                title = task.title,
                description = task.description,
                date =  task.date,
                isDone = task.isDone,
                priority = task.priority

            )
        )
    }
    override suspend fun updateTask(task: Tasks) {
        tasksDataSource.updateTask(
            TaskEntity(
                id = task.id,
                title = task.title,
                description = task.description,
                date =  task.date,
                isDone = task.isDone,
                priority = task.priority

            )
        )
    }
    override  fun getAllTasks(): Flow<List<Tasks>> {
        return tasksDataSource.getAllTasks().map {
          it.map {taskEntity->
                taskEntity.toDomain()
          }
        }
    }

    override suspend fun deleteAllTasks() {
        tasksDataSource.deleteAllTasks()
    }

    override fun searchTasks(searchQuery: String): Flow<List<Tasks>> {
        return tasksDataSource.searchTasks(searchQuery).map {
           it.map {taskEntity ->
               taskEntity.toDomain()
           }
        }
    }
}