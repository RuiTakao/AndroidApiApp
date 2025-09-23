package com.takaobrog.androidapiapp.domain.model.todo

import com.squareup.moshi.JsonClass
@JsonClass(generateAdapter = true)
data class UpdateTodoRequest(
    val title: String,
    val memo: String,
    val deviceId: String,
    val updatedAt: String,
)
