package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoApiService {
    @GET("todos/todos")
    suspend fun getTodos(): Response<List<Todo>>

    @GET("todos/todo/{todoId}")
    suspend fun getTodo(@Path("todoId") id: Int): Response<Todo>

    @POST("todos/create")
    suspend fun createTodo(
        @Body todo: Todo
    ): Response<Unit>
}