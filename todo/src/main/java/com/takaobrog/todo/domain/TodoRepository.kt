package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo

interface TodoRepository {
    suspend fun getTodo(id: String): Result<Todo?>
}