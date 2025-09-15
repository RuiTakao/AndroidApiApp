package com.takaobrog.androidapiapp.di

import com.takaobrog.androidapiapp.BuildConfig
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
    fun provideBaseUrl() = BuildConfig.BASE_URL
}