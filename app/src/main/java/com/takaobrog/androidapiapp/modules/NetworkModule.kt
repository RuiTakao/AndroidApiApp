package com.takaobrog.androidapiapp.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.squareup.moshi.Moshi
import com.takaobrog.androidapiapp.domain.local.device.DeviceRepository
import com.takaobrog.androidapiapp.domain.local.device.PostDeviceDataStoreRepository
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataApiService
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataRepository
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataRepositoryImpl
import com.takaobrog.androidapiapp.domain.remote.device.TodoApiService
import com.takaobrog.androidapiapp.domain.remote.device.TodoRepository
import com.takaobrog.androidapiapp.domain.remote.device.TodoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
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

    @Provides
    @Singleton
    fun provideTodoApi(retrofit: Retrofit): TodoApiService = retrofit.create(TodoApiService::class.java)

    @Provides
    @Singleton
    fun provideTodoRepository(impl: TodoRepositoryImpl): TodoRepository = impl

    @Provides
    @Singleton
    fun providePreferencesDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            // 既存の SharedPreferences から移行したい場合は migrations = listOf(SharedPreferencesMigration(context, "legacy_prefs"))
            produceFile = { context.preferencesDataStoreFile("app_prefs.preferences_pb") }
        )
    }
}

@InstallIn(SingletonComponent::class)
@Module
abstract class BindModule {
    @Binds @Singleton
    abstract fun bindPostRepo(
        impl: PostDeviceDataStoreRepository
    ): DeviceRepository
}