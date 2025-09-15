package com.takaobrog.androidapiapp.di

import com.takaobrog.androidapiapp.data.remote.DeviceDataApiService
import com.takaobrog.androidapiapp.data.remote.TodoApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteApiModule {

    @Provides
    @Singleton
    fun providePostDeviceDataApi(retrofit: Retrofit): DeviceDataApiService =
        retrofit.create(DeviceDataApiService::class.java)

    @Provides
    @Singleton
    fun provideTodoApi(retrofit: Retrofit): TodoApiService = retrofit.create(TodoApiService::class.java)
}
