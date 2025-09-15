package com.takaobrog.androidapiapp.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.takaobrog.androidapiapp.data.remote.DeviceDataApiService
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import com.takaobrog.androidapiapp.domain.model.DeviceData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

private val TAG = DeviceDataRepositoryImpl::class.java.simpleName

class DeviceDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: DeviceDataApiService,
): DeviceDataRepository {
    private companion object {
        val DEVICE_ID = stringPreferencesKey("device_id")
    }

    override fun deviceIdFlow(): Flow<String> =
        dataStore.data.map { prefs -> prefs[DEVICE_ID].orEmpty() }

    override suspend fun deviceId(): String =
        deviceIdFlow().first()

    override suspend fun saveDeviceId(deviceId: String) {
        Log.d(TAG, "[saveDeviceId] deviceId : $deviceId")
        dataStore.edit { it[DEVICE_ID] = deviceId }
    }

    override suspend fun postDeviceData(deviceData: DeviceData): Result<Unit>  = runCatching {
        val res = apiService.postDeviceData(deviceData)
        Log.d(TAG, "[postDeviceData] id : ${deviceData.id} deviceId : ${deviceData.deviceId}")
        if (!res.isSuccessful) throw HttpException(res)
    }
}