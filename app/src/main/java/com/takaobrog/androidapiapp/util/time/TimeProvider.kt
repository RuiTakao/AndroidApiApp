package com.takaobrog.androidapiapp.util.time

import java.time.Instant

interface TimeProvider {
    fun now(): Instant
}