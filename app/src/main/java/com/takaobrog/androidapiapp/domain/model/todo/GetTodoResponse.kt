package com.takaobrog.androidapiapp.domain.model.todo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTodoResponse(
    val id: Int? = null,
    val title: String,
    val memo: String,
    val done: Boolean = false,
    val createdAt: String,
)