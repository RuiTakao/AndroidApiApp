package com.takaobrog.androidapiapp.data.remote

import com.takaobrog.androidapiapp.domain.model.PostDeviceDataRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface DeviceDataApiService {
    @POST("devices/post/")
    suspend fun postDeviceData(
        @Body deviceData: PostDeviceDataRequest,
    ): Response<Unit>
}