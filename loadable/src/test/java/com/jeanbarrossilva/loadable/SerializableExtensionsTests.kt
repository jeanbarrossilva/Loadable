package com.jeanbarrossilva.loadable

import java.io.Serializable
import java.util.Stack
import kotlin.test.assertIs
import kotlin.test.assertNull
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SerializableExtensionsTests {
    @Test
    fun `GIVEN a Throwable WHEN converting it into a Loadable THEN it's failed`() {
        val throwable = NullPointerException()
        assertEquals(Loadable.Failed<Serializable>(throwable), throwable.loadable<Serializable>())
    }

    @Test
    fun `GIVEN a null Any WHEN converting it into a Loadable of nullable Any THEN it's loaded`() {
        assertEquals(Loadable.Loaded(null), (null as Serializable?).loadable<Serializable?>())
    }

    @Test
    fun `GIVEN a non-null Serializable WHEN converting it into a Loadable of Serializable THEN it's loaded`() { // ktlint-disable max-line-length
        assertEquals(Loadable.Loaded(0), 0.loadable<Serializable>())
    }

    @Test
    fun `GIVEN a null Any WHEN converting it into a Loadable of non-null Any THEN it's loading`() {
        assertIs<Loadable.Loading<Serializable>>((null as Serializable?).loadable<Serializable>())
    }

    @Test
    fun `GIVEN a non-null object WHEN converting it into a Loadable of a different type THEN it's null`() { // ktlint-disable max-line-length
        assertNull(0.loadable<Array<Stack<String>>>())
    }
}
