package com.jeanbarrossilva.loadable

import java.io.NotSerializableException
import kotlin.test.Test
import kotlin.test.assertFailsWith

internal class SerializabilityTests {
    @Test
    fun `GIVEN the enforced policy WHEN checking unserializable content THEN it throws`() {
        assertFailsWith<NotSerializableException> {
            Serializability.ENFORCED.check(Thread.UncaughtExceptionHandler { _, _ -> })
        }
    }

    @Test
    fun `GIVEN the enforced policy WHEN checking serializable content THEN it does not throw`() {
        Serializability.ENFORCED.check("Hello, world!")
    }

    @Test
    fun `GIVEN the ignored policy WHEN checking unserializable content THEN it does not throw`() {
        Serializability.IGNORED.check(Thread.UncaughtExceptionHandler { _, _ -> })
    }

    @Test
    fun `GIVEN the ignored policy WHEN checking serializable content THEN it does not throw`() {
        Serializability.IGNORED.check("Hello, world!")
    }
}
