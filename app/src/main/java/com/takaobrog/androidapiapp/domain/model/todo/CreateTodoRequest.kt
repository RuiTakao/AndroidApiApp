package com.takaobrog.androidapiapp.domain.model.todo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateTodoRequest(
    val title: String,
    val memo: String,
    val createdAt: String,
    val deviceId: String,
)