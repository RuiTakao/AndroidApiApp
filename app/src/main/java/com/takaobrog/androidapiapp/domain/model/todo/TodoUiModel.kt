package com.takaobrog.androidapiapp.domain.model.todo

data class TodoUiModel(
    val id: Int,
    val title: String,
    val content: String,
    val done: Boolean,
    val datetime: String,
)