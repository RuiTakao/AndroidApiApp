package com.takaobrog.androidapiapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlModule {

    @Provides
    @Named("base_url")
    fun provideBaseUrl() = "http://10.0.2.2:8765/"
}