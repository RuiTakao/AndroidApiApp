package com.takaobrog.todo.modules

import com.takaobrog.todo.domain.TodoApiService
import com.takaobrog.todo.domain.TodoRepository
import com.takaobrog.todo.domain.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
//    @Provides
//    @Named("base_url")
//    fun provideBaseUrl() = "http://10.0.2.2:8765/todos/"
//
//    @Provides
//    @Singleton
//    fun provideMoshi(): Moshi {
//        return Moshi.Builder().build()
//    }

//    @Provides
//    @Singleton
//    fun provideRetrofit(
//        moshi: Moshi,
//        @Named("base_url") baseUrl: String,
//    ): Retrofit =
//        Retrofit
//            .Builder()
//            .baseUrl(baseUrl)
//            .addConverterFactory(MoshiConverterFactory.create(moshi))
//            .build()

    @Provides
    @Singleton
    fun provideTodoApi(retrofit: Retrofit): TodoApiService = retrofit.create(TodoApiService::class.java)

    @Provides
    @Singleton
    fun provideTodoRepository(impl: TodoRepositoryImpl): TodoRepository = impl
}