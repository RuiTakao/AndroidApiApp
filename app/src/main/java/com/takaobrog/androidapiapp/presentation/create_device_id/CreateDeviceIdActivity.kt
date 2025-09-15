package com.takaobrog.androidapiapp.presentation.create_device_id

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.presentation.todo.TodoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private val TAG = CreateDeviceIdActivity::class.java.simpleName

@AndroidEntryPoint
class CreateDeviceIdActivity : AppCompatActivity() {

    private val viewModel: CreateDeviceIdViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            if (viewModel.hasDeviceId()) {
                try {
                    val res = viewModel.createDeviceId()
                    if (res.isSuccess) {
                        Log.d(TAG, "[onCreate] ${getString(R.string.create_device_id_success)}")
                        startActivity(
                            Intent(
                                this@CreateDeviceIdActivity,
                                TodoActivity::class.java
                            )
                        )
                    } else {
                        Log.e(TAG, "[onCreate] ${getString(R.string.create_device_id_dialog_network_error_title)}")
                        MaterialAlertDialogBuilder(this@CreateDeviceIdActivity)
                            .setTitle(getString(R.string.create_device_id_dialog_network_error_title))
                            .setMessage(getString(R.string.create_device_id_dialog_network_error_message))
                            .setPositiveButton(
                                getString(R.string.create_device_id_dialog_network_error_ok),
                                { dialog, which ->
                                    finish()
                                }).show()
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "[onCreate] ${getString(R.string.create_device_id_error_message)}$e")
                }
            } else {
                Log.d(TAG, "[onCreate] ${getString(R.string.create_device_id_already_created)}")
                startActivity(Intent(this@CreateDeviceIdActivity, TodoActivity::class.java))
            }
        }
    }
}