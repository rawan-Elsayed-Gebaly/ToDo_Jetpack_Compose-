package com.example.data.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)  // Fixed: Pass Task instead of Int

    @Query("SELECT * FROM task WHERE date = :date ")
    suspend fun getTaskByDate(date: Long): List<Task>

    @Query("SELECT * FROM task")
    suspend fun getAllTasks(): List<Task>
}
