package com.takaobrog.androidapiapp.domain.local.device

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DeviceRepository {
    suspend fun deviceId(): String
    suspend fun saveDeviceId(id: String)
    fun deviceIdFlow(): Flow<String>
}

class PostDeviceDataStoreRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
): DeviceRepository {
    private companion object {
        val DEVICE_ID = stringPreferencesKey("device_id")
    }

    override fun deviceIdFlow(): Flow<String> =
        dataStore.data.map { prefs -> prefs[DEVICE_ID].orEmpty() }

    override suspend fun deviceId(): String =
        deviceIdFlow().first()

    override suspend fun saveDeviceId(newDeviceId: String) {
        dataStore.edit { it[DEVICE_ID] = newDeviceId }
    }
}