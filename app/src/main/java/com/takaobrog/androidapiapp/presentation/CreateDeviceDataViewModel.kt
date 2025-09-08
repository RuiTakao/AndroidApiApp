package com.takaobrog.androidapiapp.presentation

import androidx.lifecycle.ViewModel
import com.takaobrog.androidapiapp.data.DeviceData
import com.takaobrog.androidapiapp.domain.remote.device.PostDeviceDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateDeviceDataViewModel @Inject constructor(
    private val repository: PostDeviceDataRepository
) : ViewModel() {
    suspend fun saveDeviceDataForServer(newId: String): Result<Unit> {
        return repository.postDeviceData(
            DeviceData(deviceId = newId)
        )
    }
}