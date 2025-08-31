package com.takaobrog.androidapiapp.modules

import com.squareup.moshi.Moshi
import com.takaobrog.androidapiapp.domain.TestApiService
import com.takaobrog.androidapiapp.domain.TestRepository
import com.takaobrog.androidapiapp.domain.TestRepositoryImpl
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
    fun provideBaseUrl() = "http://10.0.2.2:8765/todos/"

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
    fun provideTestApi(retrofit: Retrofit): TestApiService = retrofit.create(TestApiService::class.java)

    @Provides
    @Singleton
    fun provideTestRepository(impl: TestRepositoryImpl): TestRepository = impl
}