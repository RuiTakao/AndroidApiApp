package com.takaobrog.androidapiapp.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateTodoRequest(
    val todo: Todo,
    val deviceId: String,
)