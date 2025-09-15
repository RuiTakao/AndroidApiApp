package com.takaobrog.androidapiapp.domain.remote.device

import com.takaobrog.androidapiapp.data.Todo

interface TodoRepository {
    suspend fun getTodos(): Result<List<Todo>>

    suspend fun getTodo(id: Int): Result<Todo?>

    suspend fun createTodo(todo: Todo): Result<Unit>
}