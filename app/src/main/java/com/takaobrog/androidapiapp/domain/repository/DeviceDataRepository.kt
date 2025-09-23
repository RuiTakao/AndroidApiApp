package com.takaobrog.androidapiapp.domain.repository

interface DeviceDataRepository {
    suspend fun deviceId(): String

    suspend fun postDeviceData(): Result<Unit>
}