package com.takaobrog.todo.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todo(
    val id: Int? = null,
    val name: String,
    val deviceId: String,
)
