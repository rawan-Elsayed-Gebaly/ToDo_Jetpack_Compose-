package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.database.tasksdb.TasksDao
import com.example.data.local.database.tasksdb.TasksDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomDatabaseModule {


    @Provides
    @Singleton
    fun provideTasksDB(@ApplicationContext context:Context) =
        Room.databaseBuilder(context, TasksDatabase::class.java ,"tasksDatabase")
            .fallbackToDestructiveMigration()
            .build()
    @Provides
    fun provideTasksDao(
        db:TasksDatabase
    ):TasksDao{
       return db.getTasksDao()
    }
}