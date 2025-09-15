package com.takaobrog.androidapiapp.data.remote

import com.takaobrog.androidapiapp.domain.model.Todo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TodoApiService {
    @GET("todos/todo_list")
    suspend fun getTodoList(
        @Query("deviceId") deviceId: String,
    ): Response<List<Todo>>

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