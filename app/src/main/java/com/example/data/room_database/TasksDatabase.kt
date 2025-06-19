package com.example.data.room_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase



@Database(
    entities =[Task::class] ,
    version = 1 ,
    exportSchema = false)
abstract class TasksDatabase :RoomDatabase() {
    abstract fun getTasksDao():TasksDao

    companion object{

        private var tasksDbInstance :TasksDatabase ?=null

        fun getTasksDbInstance(context:Context):TasksDatabase{
            if(tasksDbInstance==null){
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