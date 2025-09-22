package com.takaobrog.androidapiapp.presentation.todo.component.dialog

data class TodoAlertDialog(
    val title: String,
    val message: String,
    val positiveText: String = "OK",
    val negativeText: String? = null,
)