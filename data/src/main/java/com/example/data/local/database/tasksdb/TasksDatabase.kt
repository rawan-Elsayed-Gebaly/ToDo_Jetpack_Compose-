package com.example.data.local.database.tasksdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities =[TaskEntity::class] ,
    version = 3 ,
    exportSchema = false)
abstract class TasksDatabase :RoomDatabase() {
    abstract fun getTasksDao(): TasksDao

    companion object{

        private var tasksDbInstance : TasksDatabase?=null

        fun getTasksDbInstance(context:Context): TasksDatabase {
            if(tasksDbInstance ==null){
                tasksDbInstance = Room.databaseBuilder(
                    context ,
                    TasksDatabase::class.java ,
                    name = "tasksDatabase"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()

            }
            return tasksDbInstance!!

        }
    }
}