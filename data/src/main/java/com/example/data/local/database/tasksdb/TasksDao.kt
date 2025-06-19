package com.example.data.local.database.tasksdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(taskEntity: TaskEntity)

    @Delete
    suspend fun delete(taskEntity: TaskEntity)
    @Query("Delete From task")
    suspend fun deleteAll()

    @Update
    suspend fun update(taskEntity: TaskEntity)  // Fixed: Pass Task instead of Int

    @Query("SELECT * FROM task WHERE date = :date ")
    suspend fun getTaskByDate(date: Long): List<TaskEntity>

    @Query("SELECT * FROM task")
     fun getAllTasks(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM task WHERE title LIKE '%' || :query || '%'")
    fun searchTasks(query: String): Flow<List<TaskEntity>>

}
