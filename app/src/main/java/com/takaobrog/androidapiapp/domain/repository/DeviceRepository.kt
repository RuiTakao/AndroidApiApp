package com.takaobrog.androidapiapp.domain.repository

import com.takaobrog.androidapiapp.domain.model.DeviceData
import kotlinx.coroutines.flow.Flow

interface DeviceRepository {
    suspend fun deviceId(): String

    suspend fun saveDeviceId(id: String)

    fun deviceIdFlow(): Flow<String>

    suspend fun postDeviceData(deviceData: DeviceData): Result<Unit>
}