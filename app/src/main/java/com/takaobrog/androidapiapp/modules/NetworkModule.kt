package com.takaobrog.androidapiapp.modules

import com.squareup.moshi.Moshi
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataApiService
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataRepository
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Named("base_url")
    fun provideBaseUrl() = "http://10.0.2.2:8765/"

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        moshi: Moshi,
        @Named("base_url") baseUrl: String,
    ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @Provides
    @Singleton
    fun providePostDeviceDataApi(retrofit: Retrofit): PostDeviceDataApiService =
        retrofit.create(PostDeviceDataApiService::class.java)

    @Provides
    @Singleton
    fun providePostDeviceDataRepository(impl: PostDeviceDataRepositoryImpl): PostDeviceDataRepository = impl
}