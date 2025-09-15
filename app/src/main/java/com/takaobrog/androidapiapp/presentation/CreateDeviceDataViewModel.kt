package com.takaobrog.androidapiapp.presentation

import androidx.lifecycle.ViewModel
import com.takaobrog.androidapiapp.data.DeviceData
import com.takaobrog.androidapiapp.domain.local.device.PostDeviceDataStoreRepository
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.UUID
import javax.inject.Inject

private val TAG = CreateDeviceDataViewModel::class.java.simpleName

@HiltViewModel
class CreateDeviceDataViewModel @Inject constructor(
    private val repository: PostDeviceDataRepository,
    private val local: PostDeviceDataStoreRepository
) : ViewModel() {

    suspend fun hasDeviceId() = local.deviceId().isEmpty()

    suspend fun saveDeviceData(): Result<Unit> {
        val deviceId = UUID.randomUUID().toString()
        val deviceData = DeviceData(deviceId = deviceId)
        val res = repository.postDeviceData(deviceData = deviceData)
        if (res.isSuccess) {
            local.saveDeviceId(deviceId)
        }

        return res
    }
}