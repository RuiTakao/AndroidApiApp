package com.takaobrog.androidapiapp.data.remote

import com.takaobrog.androidapiapp.domain.model.todo.CreateTodoRequest
import com.takaobrog.androidapiapp.domain.model.todo.Todo
import com.takaobrog.androidapiapp.domain.model.todo.UpdateTodoDoneRequest
import com.takaobrog.androidapiapp.domain.model.todo.UpdateTodoRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
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
        @Body createTodoRequest: CreateTodoRequest,
    ): Response<Unit>

    @PUT("todos/update/{todoId}")
    suspend fun update(
        @Path("todoId") id: Int,
        @Body updateTodoRequest: UpdateTodoRequest,
    ): Response<Unit>

    @DELETE("todos/delete/{todoId}")
    suspend fun delete(
        @Path("todoId") id: Int,
    ): Response<Unit>

    @PUT("todos/update_done/{todoId}")
    suspend fun updateDone(
        @Path("todoId") id: Int,
        @Body updateTodoDoneRequest: UpdateTodoDoneRequest,
    ): Response<Unit>
}