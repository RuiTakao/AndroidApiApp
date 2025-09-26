package com.takaobrog.androidapiapp.util.log

import android.util.Log
import com.squareup.moshi.Moshi
import okhttp3.Request

private const val TAG = "HTTP"
private const val CHUNK = 3500

fun httpLog(tag: String = TAG, request: Request, body: String? = null) {
    var requestLogTxt = "METHOD: ${request.method()}" +
            "\nPATH: ${request.url().encodedPath()}"
    if (request.headers() != null && !request.headers().toString().isEmpty()) {
        requestLogTxt += "\nHEADERS: ${request.headers()}"
    }
    if (request.url().query() != null && !request.url().query().toString().isEmpty()) {
        requestLogTxt += "\nQUERY: ${request.url().query()}"
    }

    body?.let {
        var i = 0
        while (i < body.length) {
            val end = (i + CHUNK).coerceAtMost(body.length)
            Log.i(tag, "$requestLogTxt\nJSON: ${body.substring(i, end)}")
            i = end
        }
    } ?: Log.i(tag, requestLogTxt)
}

inline fun <reified T> prettyJsonWithMoshi(value: T, moshi: Moshi): String {
    val adapter = moshi.adapter(T::class.java).indent("  ")
    return adapter.toJson(value)
}