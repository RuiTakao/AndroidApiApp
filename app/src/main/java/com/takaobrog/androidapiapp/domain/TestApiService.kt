package com.takaobrog.androidapiapp.domain

import com.takaobrog.androidapiapp.data.Test
import retrofit2.Response
import retrofit2.http.GET

interface TestApiService {
    @GET("test")
    suspend fun getTest(): Response<Test?>
}