package com.takaobrog.androidapiapp.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PostDeviceDataRequest(
    val deviceId: String,
    val deviceName: String,
)