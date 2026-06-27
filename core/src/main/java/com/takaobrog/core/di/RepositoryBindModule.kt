package com.takaobrog.core.di

import com.takaobrog.core.data.repository.DeviceDataRepositoryImpl
import com.takaobrog.core.data.repository.TodoRepositoryImpl
import com.takaobrog.core.domain.repository.DeviceDataRepository
import com.takaobrog.core.domain.repository.TodoRepository
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