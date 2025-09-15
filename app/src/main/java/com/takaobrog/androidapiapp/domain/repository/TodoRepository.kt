package com.takaobrog.androidapiapp.domain.repository

import com.takaobrog.androidapiapp.domain.model.Todo

interface TodoRepository {
    suspend fun getTodoList(): Result<List<Todo>>

    suspend fun getTodo(id: Int): Result<Todo?>

    suspend fun createTodo(todo: Todo): Result<Unit>
}