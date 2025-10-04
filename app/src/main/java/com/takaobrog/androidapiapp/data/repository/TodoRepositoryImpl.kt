package com.takaobrog.androidapiapp.data.repository

import android.util.Log
import com.squareup.moshi.Moshi
import com.takaobrog.androidapiapp.data.remote.TodoApiService
import com.takaobrog.androidapiapp.domain.model.todo.CreateTodoRequest
import com.takaobrog.androidapiapp.domain.model.todo.UpdateTodoDoneRequest
import com.takaobrog.androidapiapp.domain.model.todo.UpdateTodoRequest
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import com.takaobrog.androidapiapp.domain.repository.TodoRepository
import com.takaobrog.androidapiapp.domain.model.todo.TodoUiModel
import com.takaobrog.androidapiapp.util.log.httpLog
import com.takaobrog.androidapiapp.util.log.prettyJsonWithMoshi
import com.takaobrog.androidapiapp.util.time.TimeProvider
import retrofit2.HttpException
import java.io.IOException
import java.time.Instant
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject
import kotlin.collections.orEmpty

private val TAG = TodoRepositoryImpl::class.java.simpleName

class TodoRepositoryImpl @Inject constructor(
    private val apiService: TodoApiService,
    private val deviceDataRepository: DeviceDataRepository,
    private val time: TimeProvider,
    private val moshi: Moshi,
) : TodoRepository {
    override suspend fun getTodoList(): Result<List<TodoUiModel>> {
        return try {
            val res = apiService.getTodoList(deviceId = deviceDataRepository.deviceId())
            if (res.isSuccessful) {
                httpLog(request = res.raw().request(), body = prettyJsonWithMoshi(res.body().orEmpty(), moshi))
                val list = res.body().orEmpty().mapNotNull { item ->
                    item.id?.let { id ->
                        TodoUiModel(
                            id = id,
                            title = item.title,
                            memo = item.memo,
                            done = item.done,
                            datetime = item.createdAt.isoToDateYMD(),
                        )
                    }
                }
                Result.success(list)
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

    override suspend fun getTodo(id: Int): Result<TodoUiModel> {
        return try {
            val res = apiService.getTodo(id = id, deviceId = deviceDataRepository.deviceId())
            if (res.isSuccessful) {
                val getTodo = res.body()
                httpLog(request = res.raw().request(), body = prettyJsonWithMoshi(getTodo, moshi))
                getTodo?.id?.let { id ->
                    val todoUiModel = TodoUiModel(
                        id = id,
                        title = getTodo.title,
                        memo = getTodo.memo,
                        done = getTodo.done,
                        datetime = getTodo.createdAt.isoToDateYMD(),
                    )
                    Result.success(todoUiModel)
                } ?: Result.failure(NullPointerException())
            } else {
                Result.failure(HttpException(res))
            }
        } catch (e: IOException) {
            Result.failure(e)
        }
    }

    override suspend fun create(title: String, memo: String): Result<Unit> = runCatching {
        val now = time.now()
        val createdAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            .format(now.atOffset(ZoneOffset.UTC))

        val createGetTodoResponseRequest = CreateTodoRequest(
            title = title,
            memo = if (memo.isEmpty()) {
                "..."
            } else memo,
            createdAt = createdAt,
            deviceId = deviceDataRepository.deviceId()
        )
        val res = apiService.create(createGetTodoResponseRequest)
        httpLog(request = res.raw().request())
        if (!res.isSuccessful) throw HttpException(res)
    }

    override suspend fun update(id: Int, title: String, memo: String): Result<Unit> =
        runCatching {
            val now = time.now()
            val updatedAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .format(now.atOffset(ZoneOffset.UTC))
            val updateTodoRequest = UpdateTodoRequest(
                title = title,
                memo = memo,
                deviceId = deviceDataRepository.deviceId(),
                updatedAt = updatedAt,
            )
            val res = apiService.update(id, updateTodoRequest)
            httpLog(request = res.raw().request(), body = prettyJsonWithMoshi(updateTodoRequest, moshi))
            if (!res.isSuccessful) throw HttpException(res)
        }

    override suspend fun delete(id: Int): Result<Unit> = runCatching {
        val res = apiService.delete(id)
        httpLog(request = res.raw().request())
        if (!res.isSuccessful) throw HttpException(res)
    }

    override suspend fun updateDone(id: Int, isDone: Boolean): Result<Unit> = runCatching {
        val now = time.now()
        val updatedAt = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            .format(now.atOffset(ZoneOffset.UTC))
        val updateTodoDoneRequest = UpdateTodoDoneRequest(
            done = isDone,
            deviceId = deviceDataRepository.deviceId(),
            updatedAt = updatedAt,
        )
        val res = apiService.updateDone(id, updateTodoDoneRequest)
        httpLog(request = res.raw().request(), body = prettyJsonWithMoshi(updateTodoDoneRequest, moshi))
        if (!res.isSuccessful) throw HttpException(res)
    }

    private fun String.isoToDateYMD(zoneId: ZoneId = ZoneId.systemDefault()): String {
        val instant = Instant.parse(this)
        val dateOnlyFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy/M/d", Locale.JAPAN)
        return instant.atZone(zoneId).toLocalDate().format(dateOnlyFormatter)
    }
}