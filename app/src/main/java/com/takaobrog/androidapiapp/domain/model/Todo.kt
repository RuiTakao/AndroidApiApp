package com.takaobrog.androidapiapp.domain.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Todo(
    val id: Int? = null,
    val title: String,
    val content: String,
    val done: Boolean = false,
    val createdAt: String,
)