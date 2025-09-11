package com.takaobrog.androidapiapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.domain.local.device.PostDeviceDataStoreRepository
import com.takaobrog.todo.TodoListActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.UUID
import kotlin.getValue

private val TAG = CreateDeviceDataActivity::class.java.simpleName

val AppCompatActivity.dataStore by preferencesDataStore(name = "deviceData")

@AndroidEntryPoint
class CreateDeviceDataActivity : AppCompatActivity() {

    private val viewModel: CreateDeviceDataViewModel by viewModels()

    private val deviceDataStoreRepository by lazy {
        PostDeviceDataStoreRepository(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            println(deviceDataStoreRepository.hasDeviceData())
            if (deviceDataStoreRepository.hasDeviceData()) {
                val newDeviceId = UUID.randomUUID().toString()
                try {
                    val res = viewModel.saveDeviceDataForServer(newDeviceId)
                    if (res.isSuccess) {
                        deviceDataStoreRepository.saveDeviceDataForLocal(newDeviceId)
                    } else {
                        MaterialAlertDialogBuilder(this@CreateDeviceDataActivity)
                            .setTitle(getString(R.string.create_device_data_dialog_network_error_title))
                            .setMessage(getString(R.string.create_device_data_dialog_network_error_message))
                            .setPositiveButton(getString(R.string.create_device_data_dialog_network_error_ok), { dialog, which ->
                                finish()
                            }).show()
                    }
                } catch (e: HttpException) {
                    Log.e(TAG, "${getString(R.string.create_device_data_error_message)}$e")
                }
            }
        }
        // Todo: 呼び出し位置、修正　デバイスIDチェック後に呼び出す想定
        startActivity(Intent(this, TodoListActivity::class.java))
    }
}