package com.takaobrog.androidapiapp.domain.remote.device

import com.takaobrog.androidapiapp.data.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApiService {
    @GET("todos/todos")
    suspend fun getTodos(): Response<List<Todo>>

    @GET("todos/todo/{todoId}")
    suspend fun getTodo(
        @Path("todoId") id: Int,
        @Query("deviceId") deviceId: String,
    ): Response<Todo>

    @POST("todos/create")
    suspend fun createTodo(
        @Body todo: Todo
    ): Response<Unit>
}