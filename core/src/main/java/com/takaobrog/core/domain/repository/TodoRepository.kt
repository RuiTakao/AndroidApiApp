package com.takaobrog.core.domain.repository

import com.takaobrog.core.domain.model.todo.TodoUiModel

interface TodoRepository {
    suspend fun getTodoList(): Result<List<TodoUiModel>>

    suspend fun getTodo(id: Int): Result<TodoUiModel>

    suspend fun create(title: String, memo: String): Result<Unit>

    suspend fun update(id: Int, title: String, memo: String): Result<Unit>

    suspend fun delete(id: Int): Result<Unit>

    suspend fun updateDone(id: Int, isDone: Boolean): Result<Unit>
}