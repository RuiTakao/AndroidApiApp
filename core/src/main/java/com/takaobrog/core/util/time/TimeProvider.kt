package com.takaobrog.core.util.time

import java.time.Instant

interface TimeProvider {
    fun now(): Instant
}