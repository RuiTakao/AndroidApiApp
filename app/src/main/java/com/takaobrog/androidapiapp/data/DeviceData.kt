package com.takaobrog.androidapiapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeviceData(
    val id: Int? = null,
    val deviceId: String,
)

@JsonClass(generateAdapter = true)
data class Todo(
    val id: Int? = null,
    val title: String,
    val content: String,
    val done: Boolean,
    val deviceId: String,
    val createdAt: String,
)