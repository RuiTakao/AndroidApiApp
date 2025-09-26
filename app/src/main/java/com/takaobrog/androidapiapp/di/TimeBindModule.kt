package com.takaobrog.androidapiapp.di

import com.takaobrog.androidapiapp.util.time.TimeProvider
import com.takaobrog.androidapiapp.util.time.UtcTimeProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TimeBindModule {

    @Binds
    @Singleton
    abstract fun bindTimeProvider(impl: UtcTimeProvider): TimeProvider

}