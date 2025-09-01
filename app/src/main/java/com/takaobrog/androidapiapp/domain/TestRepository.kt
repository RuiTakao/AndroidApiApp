package com.takaobrog.androidapiapp.domain

import com.takaobrog.androidapiapp.data.Test

interface TestRepository {
    suspend fun getTest(id: String): Result<Test?>
}