package com.takaobrog.todo.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todo(
    val id: Int? = null,
    val title: String,
    val content: String,
    val done: Boolean,
    val deviceId: String,
    val createdAt: String,
)
