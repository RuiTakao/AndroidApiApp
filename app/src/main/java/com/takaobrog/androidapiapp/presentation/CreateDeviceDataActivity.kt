package com.takaobrog.androidapiapp.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takaobrog.androidapiapp.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

private val TAG = CreateDeviceDataActivity::class.java.simpleName

@AndroidEntryPoint
class CreateDeviceDataActivity : AppCompatActivity() {

    private val viewModel: CreateDeviceDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            if (viewModel.hasDeviceId()) {
                try {
                    val res = viewModel.saveDeviceData()
                    if (res.isSuccess) {
                        startActivity(
                            Intent(
                                this@CreateDeviceDataActivity,
                                TodoActivity::class.java
                            )
                        )
                    } else {
                        MaterialAlertDialogBuilder(this@CreateDeviceDataActivity)
                            .setTitle(getString(R.string.create_device_data_dialog_network_error_title))
                            .setMessage(getString(R.string.create_device_data_dialog_network_error_message))
                            .setPositiveButton(
                                getString(R.string.create_device_data_dialog_network_error_ok),
                                { dialog, which ->
                                    finish()
                                }).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "${getString(R.string.create_device_data_error_message)}$e")
                }
            } else {
                startActivity(Intent(this@CreateDeviceDataActivity, TodoActivity::class.java))
            }
        }
    }
}