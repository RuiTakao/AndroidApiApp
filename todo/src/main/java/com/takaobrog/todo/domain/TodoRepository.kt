package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo

interface TodoRepository {
    suspend fun getTodos(): Result<List<Todo>>
}