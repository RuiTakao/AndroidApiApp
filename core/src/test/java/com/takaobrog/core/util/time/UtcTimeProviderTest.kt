package com.takaobrog.core.util.time

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.Clock
import java.time.Instant

class UtcTimeProviderTest {

    private val clock: Clock = mockk()
    private val timeProvider = UtcTimeProvider(clock)

    @Test
    fun `now returns expected instant from clock`() {
        // Given
        val expectedInstant = Instant.parse("2023-01-01T00:00:00Z")
        every { clock.instant() } returns expectedInstant

        // When
        val actualInstant = timeProvider.now()

        // Then
        assertEquals(expectedInstant, actualInstant)
    }
}
