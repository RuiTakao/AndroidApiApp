package com.takaobrog.androidapiapp.presentation.create_device_id

import androidx.lifecycle.ViewModel
import com.takaobrog.androidapiapp.domain.model.DeviceData
import com.takaobrog.androidapiapp.domain.repository.DeviceDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class CreateDeviceIdViewModel @Inject constructor(
    private val repository: DeviceDataRepository,
) : ViewModel() {

    suspend fun hasDeviceId() = repository.deviceId().isEmpty()

    suspend fun createDeviceId(): Result<Unit> {
        val deviceId = UUID.randomUUID().toString()
        val deviceData = DeviceData(deviceId = deviceId)
        val res = repository.postDeviceData(deviceData = deviceData)
        if (res.isSuccess) {
            repository.saveDeviceId(deviceId)
        }

        return res
    }
}