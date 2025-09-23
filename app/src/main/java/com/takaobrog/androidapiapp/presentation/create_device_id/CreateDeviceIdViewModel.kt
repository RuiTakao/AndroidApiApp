package com.takaobrog.androidapiapp.presentation.create_device_id

import androidx.lifecycle.ViewModel
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateDeviceIdViewModel @Inject constructor(
    private val repository: DeviceDataRepository,
) : ViewModel() {

    suspend fun hasDeviceId() = repository.deviceId().isEmpty()

    suspend fun createDeviceId(): Result<Unit> {
        val res = repository.postDeviceData()
        return res
    }
}