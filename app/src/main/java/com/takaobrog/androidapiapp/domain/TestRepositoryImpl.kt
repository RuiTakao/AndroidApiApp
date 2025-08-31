package com.takaobrog.androidapiapp.domain

import com.takaobrog.androidapiapp.data.Test
import retrofit2.HttpException
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor(
    private val apiService: TestApiService,
): TestRepository {
    override suspend fun getTest(): Result<Test?> {
        val res = apiService.getTest()
        return if (res.isSuccessful) {
            Result.success(res.body())
        } else {
            Result.failure(HttpException(res))
        }
    }
}