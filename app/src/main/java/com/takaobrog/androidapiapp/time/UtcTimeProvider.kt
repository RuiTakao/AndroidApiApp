package com.takaobrog.androidapiapp.time

import java.time.Clock
import javax.inject.Inject

class UtcTimeProvider @Inject constructor(
    private val clock: Clock
): TimeProvider {
    override fun now() = java.time.Instant.now(clock)
}