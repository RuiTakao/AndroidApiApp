package com.takaobrog.androidapiapp.domain.local.device

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class PostDeviceDataStoreRepository(
    private val dataStore: DataStore<Preferences>,
) {
    private companion object {
        val DEVICE_ID = stringPreferencesKey("device_id")
    }

    suspend fun hasDeviceData() = dataStore.data.first()[DEVICE_ID] == null

    suspend fun saveDeviceDataForLocal(newDeviceId: String) {
        dataStore.edit { it[DEVICE_ID] = newDeviceId }
    }
}