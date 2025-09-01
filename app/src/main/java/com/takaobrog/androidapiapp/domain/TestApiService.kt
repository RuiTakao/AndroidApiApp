package com.takaobrog.androidapiapp.domain

import com.takaobrog.androidapiapp.data.Test
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TestApiService {
    @GET("test/{id}")
    suspend fun getTest(@Path("id") id: String): Response<Test?>
}