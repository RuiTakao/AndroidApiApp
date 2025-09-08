package com.takaobrog.androidapiapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceData(
    val id: Int? = null,
    val deviceId: String,
)
