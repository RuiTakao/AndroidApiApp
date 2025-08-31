package com.takaobrog.androidapiapp.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Test(
    val id: Int? = null,
    val name: String,
)
