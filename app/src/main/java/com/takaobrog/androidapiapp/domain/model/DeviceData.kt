package com.takaobrog.androidapiapp.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceData(
    val id: Int? = null,
    val deviceId: String,
)