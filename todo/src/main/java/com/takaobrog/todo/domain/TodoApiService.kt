package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApiService {
    @GET("todos/todos")
    suspend fun getTodos(): Response<List<Todo>>

    @POST("todos/create")
    suspend fun createTodo(
        @Body todo: Todo
    ): Response<Unit>
}