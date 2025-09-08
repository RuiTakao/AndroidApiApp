package com.takaobrog.androidapiapp.domain.remote.device

import com.takaobrog.androidapiapp.data.DeviceData
import retrofit2.HttpException
import javax.inject.Inject

class PostDeviceDataRepositoryImpl @Inject constructor(
    val apiService: PostDeviceDataApiService,
) : PostDeviceDataRepository {
    override suspend fun postDeviceData(deviceData: DeviceData): Result<Unit> = runCatching {
        val res = apiService.postDeviceData(deviceData)
        if (!res.isSuccessful) throw HttpException(res)
    }
}