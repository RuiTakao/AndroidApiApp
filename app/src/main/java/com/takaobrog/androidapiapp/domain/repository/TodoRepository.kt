package com.takaobrog.androidapiapp.domain.repository

import com.takaobrog.androidapiapp.domain.model.todo.TodoUiModel

interface TodoRepository {
    suspend fun getTodoList(): Result<List<TodoUiModel>>

    suspend fun getTodo(id: Int): Result<TodoUiModel>

    suspend fun create(title: String, content: String): Result<Unit>

    suspend fun update(id: Int, title: String, content: String): Result<Unit>

    suspend fun delete(id: Int): Result<Unit>

    suspend fun updateDone(id: Int, isDone: Boolean): Result<Unit>
}