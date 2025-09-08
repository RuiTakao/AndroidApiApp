package com.takaobrog.androidapiapp.domain.remote.device

import com.takaobrog.androidapiapp.data.DeviceData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface PostDeviceDataApiService {
    @POST("devices/post/")
    suspend fun postDeviceData(
        @Body deviceData: DeviceData,
    ): Response<Unit>
}