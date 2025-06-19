package com.example.data.di

import com.example.data.repositoryimpl.FirebaseAuthRepositoryImpl
import com.example.data.repositoryimpl.TasksRepositoryImpl
import com.example.domain.repository.firebaserepository.FirebaseRepository
import com.example.domain.repository.tasksrepository.TasksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
 abstract class RepositoryModule {

     @Binds
     abstract fun provideTasksRepository(
         tasksRepositoryImpl: TasksRepositoryImpl
     ):TasksRepository

     @Binds
     abstract fun provideFirebaseAuthRepository(
         firebaseAuthRepoImpl:FirebaseAuthRepositoryImpl
     ):FirebaseRepository
}