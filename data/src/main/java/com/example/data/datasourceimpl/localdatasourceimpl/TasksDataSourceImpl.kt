package com.example.data.datasourceimpl.localdatasourceimpl

import com.example.data.datasource.localdatasource.TasksDataSource
import com.example.data.local.database.tasksdb.TaskEntity
import com.example.data.local.database.tasksdb.TasksDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TasksDataSourceImpl @Inject constructor(
    private  val tasksDao: TasksDao
) :TasksDataSource{
    override suspend fun insertTask(taskEntity: TaskEntity) {
        try {
            tasksDao.insertTask(taskEntity)
        }catch (ex:Exception){
            throw ex
        }

    }

    override suspend fun deleteTask(taskEntity: TaskEntity) {
        try {
            tasksDao.delete(taskEntity)
        }catch (ex:Exception){
            throw ex
        }
    }

    override suspend fun updateTask(taskEntity: TaskEntity) {
        try {
            tasksDao.update(taskEntity)
        }catch (ex:Exception){
            throw ex
        }
    }

    override  fun getAllTasks(): Flow<List<TaskEntity>> {
        try {
            return tasksDao.getAllTasks().map {taskEntity ->
                taskEntity
            }
        }catch (ex:Exception){
            throw ex
        }
    }

    override suspend fun deleteAllTasks() {
        tasksDao.deleteAll()
    }

    override fun searchTasks(searchQuery: String): Flow<List<TaskEntity>> {
        return tasksDao.searchTasks(searchQuery)
    }
}