package com.takaobrog.androidapiapp.di

import com.takaobrog.androidapiapp.data.repository.PostDeviceDataRepositoryImpl
import com.takaobrog.androidapiapp.data.repository.TodoRepositoryImpl
import com.takaobrog.androidapiapp.domain.repository.DeviceRepository
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
        impl: PostDeviceDataRepositoryImpl
    ): DeviceRepository

    @Binds @Singleton
    abstract fun bindTodoRepository(
        impl: TodoRepositoryImpl
    ): TodoRepository
}