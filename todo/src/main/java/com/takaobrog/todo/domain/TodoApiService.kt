package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo
import retrofit2.Response
import retrofit2.http.GET

interface TodoApiService {
    @GET("todos/todos")
    suspend fun getTodos(): Response<List<Todo>>
}