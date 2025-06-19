package com.example.data.di

import com.example.data.datasource.localdatasource.TasksDataSource
import com.example.data.datasource.remote.FirebaseAuthDataSource
import com.example.data.datasourceimpl.localdatasourceimpl.TasksDataSourceImpl
import com.example.data.datasourceimpl.remotedatasource.FirebaseAuthDataSourceImpl
import com.google.firebase.Firebase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun provideTasksDataSource(
        dataSource: TasksDataSourceImpl
    ):TasksDataSource

    @Binds
    abstract fun provideFirebaseAuthDataSource(
        dataSource: FirebaseAuthDataSourceImpl
    ):FirebaseAuthDataSource


}