package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo

interface TodoRepository {
    suspend fun getTodos(): Result<List<Todo>>

    suspend fun createTodo(todo: Todo): Result<Unit>
}