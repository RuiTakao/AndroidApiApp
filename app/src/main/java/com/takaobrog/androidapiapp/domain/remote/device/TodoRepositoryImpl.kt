package com.takaobrog.androidapiapp.domain.remote.device

import com.takaobrog.androidapiapp.data.Todo
import com.takaobrog.androidapiapp.domain.local.device.PostDeviceDataStoreRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val apiService: TodoApiService,
    private val deviceDataStoreRepository: PostDeviceDataStoreRepository,
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