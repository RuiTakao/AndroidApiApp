package com.takaobrog.androidapiapp.domain.model.todo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateTodoRequest(
    val todo: Todo,
    val deviceId: String,
)