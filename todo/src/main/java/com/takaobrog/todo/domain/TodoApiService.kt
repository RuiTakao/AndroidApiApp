package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TodoApiService {
    @GET("todos/test/{id}")
    suspend fun getTodo(@Path("id") id: String): Response<Todo?>
}