package com.takaobrog.androidapiapp

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.takaobrog.androidapiapp.data.TestDataStoreRepository
import dagger.hilt.android.HiltAndroidApp

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "setting"
)

@HiltAndroidApp
class Application: Application() {
    lateinit var testDataStoreRepository: TestDataStoreRepository

    override fun onCreate() {
        super.onCreate()
        testDataStoreRepository = TestDataStoreRepository(dataStore)
    }
}