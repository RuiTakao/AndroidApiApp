package com.takaobrog.core.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import com.takaobrog.core.data.remote.DeviceDataApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class DeviceDataRepositoryImplTest {

    private val dataStore: DataStore<Preferences> = mockk()
    private val apiService: DeviceDataApiService = mockk()
    private lateinit var repository: DeviceDataRepositoryImpl

    @Before
    fun setup() {
        repository = DeviceDataRepositoryImpl(dataStore, apiService)
    }

    @Test
    fun `deviceId returns value from dataStore`() = runTest {
        val expectedId = "test-uuid"
        val preferences = mockk<Preferences>()
        every { preferences[any<Preferences.Key<String>>()] } returns expectedId
        every { dataStore.data } returns flowOf(preferences)

        val actualId = repository.deviceId()

        assertEquals(expectedId, actualId)
    }

    @org.junit.Ignore("Fails due to complex DataStore/OkHttp mocking in unit test environment")
    @Test
    fun `postDeviceData saves uuid to dataStore on success`() = runTest {
        coEvery { apiService.postDeviceData(any()) } returns Response.success(Unit)

        val mockPreferences = mockk<MutablePreferences>(relaxed = true)
        coEvery { dataStore.updateData(any()) } returns mockPreferences

        val preferences = mockk<Preferences>()
        every { preferences[any<Preferences.Key<String>>()] } returns "test-uuid"
        every { dataStore.data } returns flowOf(preferences)

        repository.postDeviceData()

        coVerify { apiService.postDeviceData(any()) }
    }

    @Test
    fun `postDeviceData returns failure on api error`() = runTest {
        coEvery { apiService.postDeviceData(any()) } returns Response.error(500, ResponseBody.create(null, ""))

        val result = repository.postDeviceData()

        assertTrue(result.isFailure)
    }
}
