package com.takaobrog.androidapiapp.util.time

import java.time.Clock
import java.time.Instant
import javax.inject.Inject

class UtcTimeProvider @Inject constructor(
    private val clock: Clock
): TimeProvider {
    override fun now() = Instant.now(clock)
}