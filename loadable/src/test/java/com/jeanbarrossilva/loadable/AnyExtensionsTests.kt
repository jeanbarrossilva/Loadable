package com.jeanbarrossilva.loadable

import java.util.Stack
import kotlin.test.Test
import kotlin.test.assertIs
import kotlin.test.assertNull
import org.junit.Assert.assertEquals

internal class AnyExtensionsTests {
    @Test
    fun `GIVEN a Throwable WHEN converting it into a Loadable THEN it's failed`() {
        val throwable = NullPointerException()
        assertEquals(Loadable.Failed<Any?>(throwable), throwable.loadable<Any?>())
    }

    @Test
    fun `GIVEN a null Any WHEN converting it into a Loadable of nullable Any THEN it's loaded`() {
        assertEquals(Loadable.Loaded(null), null.loadable<Any?>())
    }

    @Test
    fun `GIVEN a non-null Any WHEN converting it into a Loadable of nullable Any THEN it's loaded`() { // ktlint-disable max-line-length
        assertEquals(Loadable.Loaded(0), 0.loadable<Any?>())
    }

    @Test
    fun `GIVEN a null Any WHEN converting it into a Loadable of non-null Any THEN it's loading`() {
        assertIs<Loadable.Loading<Any>>(null.loadable<Any>())
    }

    @Test
    fun `GIVEN a non-null object WHEN converting it into a Loadable of a different type THEN it's null`() { // ktlint-disable max-line-length
        assertNull(0.loadable<Array<Stack<String>>>())
    }
}
