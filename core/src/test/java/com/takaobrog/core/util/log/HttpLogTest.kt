package com.takaobrog.core.util.log

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Request
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class HttpLogTest {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @Test
    fun `prettyJsonWithMoshi formats data class correctly`() {
        // Given
        val testData = TestData(id = 1, name = "Test")
        val expectedJson = """
            {
              "id": 1,
              "name": "Test"
            }
        """.trimIndent()

        // When
        val actualJson = prettyJsonWithMoshi(testData, moshi)

        // Then
        assertEquals(expectedJson, actualJson)
    }

    @Test
    fun `httpLog call with mock request does not throw exception`() {
        val request = Request.Builder()
            .url("https://example.com/test?query=1")
            .header("X-Test", "Value")
            .build()

        httpLog(request = request, body = "{\"status\":\"ok\"}")

        assertTrue(true)
    }

    data class TestData(val id: Int, val name: String)
}
