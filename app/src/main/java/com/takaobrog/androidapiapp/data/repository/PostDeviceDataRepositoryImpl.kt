package com.takaobrog.androidapiapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.takaobrog.androidapiapp.data.remote.DeviceDataApiService
import com.takaobrog.androidapiapp.domain.repository.DeviceRepository
import com.takaobrog.androidapiapp.domain.model.DeviceData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

class PostDeviceDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: DeviceDataApiService,
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

    override suspend fun postDeviceData(deviceData: DeviceData): Result<Unit>  = runCatching {
        val res = apiService.postDeviceData(deviceData)
        if (!res.isSuccessful) throw HttpException(res)
    }
}