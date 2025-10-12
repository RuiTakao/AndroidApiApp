package com.takaobrog.androidapiapp.presentation.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.databinding.ActivityTodoBinding
import com.takaobrog.androidapiapp.presentation.create_device_id.CreateDeviceIdActivity
import com.takaobrog.androidapiapp.presentation.create_device_id.DeviceDataViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private val TAG = TodoActivity::class.java.simpleName

@AndroidEntryPoint
class TodoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoBinding

    private val deviceDataViewModel: DeviceDataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        hasDeviceId()

        binding = ActivityTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.todoToolbar)

        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_todo_fragment_content_main) as NavHostFragment)
                .navController

        setupActionBarWithNavController(navController = navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            (supportFragmentManager.findFragmentById(R.id.nav_host_todo_fragment_content_main) as NavHostFragment)
                .navController

        navController.previousBackStackEntry
            ?.savedStateHandle
            ?.set("reload", true)

        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun hasDeviceId() {
        lifecycleScope.launch {
            if (deviceDataViewModel.hasDeviceId())
                startActivity(
                    Intent(
                        this@TodoActivity,
                        CreateDeviceIdActivity::class.java
                    )
                )
            else Log.d(TAG, "[onCreate] ${getString(R.string.create_device_id_already_created)}")
        }
    }
}