package com.takaobrog.androidapiapp.domain.model.todo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UpdateTodoDoneRequest(
    val done: Boolean,
    val deviceId: String,
)
