package com.takaobrog.androidapiapp.di

import com.takaobrog.androidapiapp.data.repository.DeviceDataRepositoryImpl
import com.takaobrog.androidapiapp.data.repository.TodoRepositoryImpl
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryBindModule {

    @Binds @Singleton
    abstract fun bindDeviceRepository(
        impl: DeviceDataRepositoryImpl
    ): DeviceDataRepository

    @Binds @Singleton
    abstract fun bindTodoRepository(
        impl: TodoRepositoryImpl
    ): TodoRepository
}