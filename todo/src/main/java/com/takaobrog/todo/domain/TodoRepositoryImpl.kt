package com.takaobrog.todo.domain

import com.takaobrog.todo.data.Todo
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val apiService: TodoApiService
) : TodoRepository {
    override suspend fun getTodos(): Result<List<Todo>> {
        return try {
            val res = apiService.getTodos()
            if (res.isSuccessful) {
                Result.success(res.body().orEmpty())
            } else {
                Result.failure(HttpException(res))
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createTodo(todo: Todo): Result<Unit> = runCatching {
        val res = apiService.createTodo(todo)
        if (!res.isSuccessful) throw HttpException(res)
    }
}