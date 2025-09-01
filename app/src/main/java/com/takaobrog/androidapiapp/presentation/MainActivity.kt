package com.takaobrog.androidapiapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.takaobrog.androidapiapp.R
import com.takaobrog.androidapiapp.data.TestDataStoreRepository
import com.takaobrog.androidapiapp.domain.TestRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

val AppCompatActivity.dataStore by preferencesDataStore(name = "settings")

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // Todo API Test
    @Inject lateinit var repository: TestRepository

    // Todo DataStore preferences Test
    private val testDataStoreRepository by lazy {
        TestDataStoreRepository(dataStore)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Todo API Test & DataStore preferences Test
        lifecycleScope.launch {
            testDataStoreRepository.currentUserName.collect {
                val result = repository.getTest(it)
                if (result.isSuccess) {
                    Log.d("Api Test isSuccess", result.toString())
                } else {
                    Log.d("Api Test isFailure", result.toString())

                }
            }
        }
        lifecycleScope.launch {
            testDataStoreRepository.saveUserName("TestName")
        }
    }
}