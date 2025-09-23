package com.takaobrog.androidapiapp.data.repository

import android.os.Build
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.takaobrog.androidapiapp.data.remote.DeviceDataApiService
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import com.takaobrog.androidapiapp.domain.model.PostDeviceDataRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.util.UUID
import javax.inject.Inject

private val TAG = DeviceDataRepositoryImpl::class.java.simpleName

class DeviceDataRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val apiService: DeviceDataApiService,
) : DeviceDataRepository {
    override suspend fun deviceId(): String =
        deviceIdFlow().first()

    override suspend fun postDeviceData(): Result<Unit> = runCatching {
        val uuid = UUID.randomUUID().toString()
        val deviceName = Build.MODEL
        val deviceData = PostDeviceDataRequest(deviceId = uuid, deviceName = deviceName)
        val res = apiService.postDeviceData(deviceData)
        Log.d(TAG, "[postDeviceData] post deviceId : $uuid post deviceName : $deviceName")
        if (res.isSuccessful) {
            dataStore.edit { it[DEVICE_ID] = uuid }
            Log.d(TAG, "[postDeviceData] save local deviceId : ${deviceId()}")
        } else throw HttpException(res)
    }

    private fun deviceIdFlow(): Flow<String> =
        dataStore.data.map { prefs -> prefs[DEVICE_ID].orEmpty() }

    private companion object {
        val DEVICE_ID = stringPreferencesKey("device_id")
    }
}