package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo
import retrofit2.HttpException
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val apiService: TodoApiService
) : TodoRepository {
    override suspend fun getTodo(id: String): Result<Todo?> {
        val res = apiService.getTodo(id)
        return if (res.isSuccessful) {
            Result.success(res.body())
        } else {
            Result.failure(HttpException(res))
        }
    }
}