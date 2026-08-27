package com.takaobrog.core.data.repository

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.takaobrog.core.data.remote.TodoApiService
import com.takaobrog.core.domain.model.todo.GetTodoResponse
import com.takaobrog.core.domain.repository.DeviceDataRepository
import com.takaobrog.core.util.time.TimeProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.time.Instant

class TodoRepositoryImplTest {

    private val apiService: TodoApiService = mockk()
    private val deviceDataRepository: DeviceDataRepository = mockk()
    private val time: TimeProvider = mockk()
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    private lateinit var repository: TodoRepositoryImpl

    @Before
    fun setup() {
        repository = TodoRepositoryImpl(apiService, deviceDataRepository, time, moshi)
    }

    @Test
    fun `getTodoList returns list on success`() = runTest {
        val deviceId = "test-device-id"
        val apiResponse = listOf(
            GetTodoResponse(id = 1, title = "Task 1", memo = "Memo 1", done = false, createdAt = "2023-01-01T00:00:00Z")
        )
        coEvery { deviceDataRepository.deviceId() } returns deviceId
        coEvery { apiService.getTodoList(deviceId) } returns Response.success(apiResponse)

        val result = repository.getTodoList()

        assertTrue(result.isSuccess)
        val list = result.getOrNull()
        assertEquals(1, list?.size)
        assertEquals("Task 1", list?.get(0)?.title)
        assertTrue(list?.get(0)?.datetime?.contains("/") ?: false)
    }

    @Test
    fun `getTodoList returns empty list when deviceId is empty`() = runTest {
        coEvery { deviceDataRepository.deviceId() } returns ""

        val result = repository.getTodoList()

        assertTrue(result.isSuccess)
        assertTrue(result.getOrNull()?.isEmpty() ?: false)
    }

    @Test
    fun `getTodoList returns failure on api error`() = runTest {
        val deviceId = "test-device-id"
        coEvery { deviceDataRepository.deviceId() } returns deviceId
        coEvery { apiService.getTodoList(deviceId) } returns Response.error(404, ResponseBody.create(null, ""))

        val result = repository.getTodoList()

        assertTrue(result.isFailure)
    }

    @Test
    fun `getTodo returns model on success`() = runTest {
        val deviceId = "test-device-id"
        val todoId = 1
        val apiResponse = GetTodoResponse(id = todoId, title = "Task 1", memo = "Memo 1", done = false, createdAt = "2023-01-01T00:00:00Z")
        coEvery { deviceDataRepository.deviceId() } returns deviceId
        coEvery { apiService.getTodo(todoId, deviceId) } returns Response.success(apiResponse)

        val result = repository.getTodo(todoId)

        assertTrue(result.isSuccess)
        assertEquals("Task 1", result.getOrNull()?.title)
    }

    @Test
    fun `create returns success`() = runTest {
        val deviceId = "test-device-id"
        coEvery { deviceDataRepository.deviceId() } returns deviceId
        coEvery { time.now() } returns Instant.parse("2023-01-01T00:00:00Z")
        coEvery { apiService.create(any()) } returns Response.success(Unit)

        val result = repository.create("Title", "Memo")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `update returns success`() = runTest {
        val deviceId = "test-device-id"
        val todoId = 1
        coEvery { deviceDataRepository.deviceId() } returns deviceId
        coEvery { time.now() } returns Instant.parse("2023-01-01T00:00:00Z")
        coEvery { apiService.update(todoId, any()) } returns Response.success(Unit)

        val result = repository.update(todoId, "Title", "Memo")

        assertTrue(result.isSuccess)
    }

    @Test
    fun `delete returns success`() = runTest {
        val todoId = 1
        coEvery { apiService.delete(todoId) } returns Response.success(Unit)

        val result = repository.delete(todoId)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `updateDone returns success`() = runTest {
        val deviceId = "test-device-id"
        val todoId = 1
        coEvery { deviceDataRepository.deviceId() } returns deviceId
        coEvery { time.now() } returns Instant.parse("2023-01-01T00:00:00Z")
        coEvery { apiService.updateDone(todoId, any()) } returns Response.success(Unit)

        val result = repository.updateDone(todoId, true)

        assertTrue(result.isSuccess)
    }
}
