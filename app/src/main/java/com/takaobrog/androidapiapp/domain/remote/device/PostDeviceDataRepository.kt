package com.takaobrog.androidapiapp.domain.remote.device

import com.takaobrog.androidapiapp.data.DeviceData

interface PostDeviceDataRepository {
    suspend fun postDeviceData(deviceData: DeviceData): Result<Unit>
}