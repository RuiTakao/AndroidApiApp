package com.takaobrog.androidapiapp.data.repository

import android.util.Log
import com.takaobrog.androidapiapp.data.remote.TodoApiService
import com.takaobrog.androidapiapp.domain.model.todo.CreateTodoRequest
import com.takaobrog.androidapiapp.domain.model.todo.Todo
import com.takaobrog.androidapiapp.domain.model.todo.UpdateTodoDoneRequest
import com.takaobrog.androidapiapp.domain.model.todo.UpdateTodoRequest
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import com.takaobrog.androidapiapp.time.TimeProvider
import retrofit2.HttpException
import java.io.IOException
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.collections.orEmpty

private val TAG = TodoRepositoryImpl::class.java.simpleName

class TodoRepositoryImpl @Inject constructor(
    private val apiService: TodoApiService,
    private val deviceDataRepository: DeviceDataRepository,
    private val time: TimeProvider,
) : TodoRepository {
    override suspend fun getTodoList(): Result<List<Todo>> {
        return try {
            val res = apiService.getTodoList(deviceId = deviceDataRepository.deviceId())
            if (res.isSuccessful) {
                Log.d(TAG, "[getTodoList] success ${res.body().orEmpty()}")
                Result.success(res.body().orEmpty())
            } else {
                Log.e(TAG, "[getTodoList] failure")
                Result.failure(HttpException(res))
            }
        } catch (e: IOException) {
            Log.e(TAG, "[getTodoList] IOException : $e")
            Result.failure(e)
        } catch (e: Exception) {
            Log.e(TAG, "[getTodoList] Exception : $e")
            Result.failure(e)
        }
    }

    override suspend fun getTodo(id: Int): Result<Todo?> {
        return try {
            val res = apiService.getTodo(id = id, deviceId = deviceDataRepository.deviceId())
            if (res.isSuccessful) {
                Result.success(res.body())
            } else {
                Result.failure(HttpException(res))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun createTodo(title: String, content: String): Result<Unit> = runCatching {
        val now = time.now()
        val createdAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            .format(now.atOffset(ZoneOffset.UTC))

        val createTodoRequest = CreateTodoRequest(
            todo = Todo(
                title = title,
                content = content,
                createdAt = createdAt,
            ),
            deviceId = deviceDataRepository.deviceId()
        )
        val res = apiService.createTodo(createTodoRequest)
        if (!res.isSuccessful) throw HttpException(res)
    }

    override suspend fun update(id: Int, title: String, content: String): Result<Unit> = runCatching {
        val now = time.now()
        val updatedAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            .format(now.atOffset(ZoneOffset.UTC))
        val updateTodoRequest = UpdateTodoRequest(
            title = title,
            content = content,
            deviceId = deviceDataRepository.deviceId(),
            updatedAt = updatedAt,
        )
        val res = apiService.update(id, updateTodoRequest)
        if (!res.isSuccessful) throw HttpException(res)
    }

    override suspend fun delete(id: Int): Result<Unit> = runCatching {
        val res = apiService.delete(id)
        if (!res.isSuccessful) throw HttpException(res)
    }

    override suspend fun updateDone(id: Int, isDone: Boolean): Result<Unit> = runCatching {
        val updateTodoDoneRequest = UpdateTodoDoneRequest(
            done = isDone,
            deviceId = deviceDataRepository.deviceId(),
        )
        val res = apiService.updateDone(id, updateTodoDoneRequest)
        if (!res.isSuccessful) throw HttpException(res)
    }
}