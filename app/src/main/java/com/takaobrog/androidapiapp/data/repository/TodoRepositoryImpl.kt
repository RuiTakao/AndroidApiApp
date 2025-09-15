package com.takaobrog.androidapiapp.data.repository

import android.util.Log
import com.takaobrog.androidapiapp.data.remote.TodoApiService
import com.takaobrog.androidapiapp.domain.model.Todo
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.collections.orEmpty

private val TAG = TodoRepositoryImpl::class.java.simpleName

class TodoRepositoryImpl @Inject constructor(
    private val apiService: TodoApiService,
    private val deviceDataStoreRepository: DeviceDataRepositoryImpl,
) : TodoRepository {
    override suspend fun getTodoList(): Result<List<Todo>> {
        return try {
            val res = apiService.getTodos()
            if (res.isSuccessful) {
                Log.d(TAG, "[getTodoList] success ${res.body().orEmpty()}")
                Result.success(res.body().orEmpty())
            } else {
                Log.d(TAG, "[getTodoList] failure")
                Result.failure(HttpException(res))
            }
        } catch (e: IOException) {
            Log.d(TAG, "[getTodoList] IOException : $e")
            Result.failure(e)
        } catch (e: Exception) {
            Log.d(TAG, "[getTodoList] Exception : $e")
            Result.failure(e)
        }
    }

    override suspend fun getTodo(id: Int): Result<Todo?> {
        return try {
            val res = apiService.getTodo(id, deviceDataStoreRepository.deviceId())
            if (res.isSuccessful) {
                Result.success(res.body())
            } else {
                Result.failure(HttpException(res))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun createTodo(todo: Todo): Result<Unit> = runCatching {
        val res = apiService.createTodo(todo)
        if (!res.isSuccessful) throw HttpException(res)
    }
}