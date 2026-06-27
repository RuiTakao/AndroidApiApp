package com.takaobrog.core.domain.model.todo

data class TodoUiModel(
    val id: Int,
    val title: String,
    val memo: String,
    val done: Boolean,
    val datetime: String,
)