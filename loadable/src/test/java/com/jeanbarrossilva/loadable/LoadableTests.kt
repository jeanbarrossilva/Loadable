package com.jeanbarrossilva.loadable

import kotlin.test.assertIs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

internal class LoadableTests {
    @Test
    fun `GIVEN a Loading Loadable WHEN getting its content THEN it's null`() {
        assertNull(Loadable.Loading<Int>().contentOrNull)
    }

    @Test
    fun `GIVEN a Loaded Loadable WHEN getting its content THEN it isn't null`() {
        assertEquals(0, Loadable.Loaded(0).contentOrNull)
    }

    @Test
    fun `GIVEN a Failed Loadable WHEN getting its content THEN it's null`() {
        assertNull(Loadable.Failed<Int>(Throwable()).contentOrNull)
    }

    @Test
    fun `GIVEN a Loading Loadable WHEN calling ifLoaded on it THEN the callback isn't run`() {
        assertIfLoadedCallbackRun(false, Loadable.Loading<Int>())
    }

    @Test
    fun `GIVEN a Loaded Loadable WHEN calling ifLoaded on it THEN the callback is run`() {
        assertIfLoadedCallbackRun(true, Loadable.Loaded(0))
    }

    @Test
    fun `GIVEN a Failed Loadable WHEN calling ifLoaded on it THEN the callback isn't run`() {
        assertIfLoadedCallbackRun(false, Loadable.Failed<Int>(Throwable()))
    }

    @Test
    fun `GIVEN a Loading Loadable WHEN mapping it THEN it's unchanged`() {
        assertIs<Loadable.Loading<Int>>(Loadable.Loading<String>().map(String::length))
    }

    @Test
    fun `GIVEN a Loaded Loadable WHEN mapping it THEN its content is transformed`() {
        assertEquals(
            Loadable.Loaded("Hello, ").map { it + "Jean!" },
            Loadable.Loaded("Hello, Jean!")
        )
    }

    @Test
    fun `GIVEN a Failed Loadable WHEN mapping it THEN it's unchanged`() {
        assertIs<Loadable.Failed<Char>>(Loadable.Failed<Int>(Throwable()).map(::Char))
    }

    private fun assertIfLoadedCallbackRun(expected: Boolean, loadable: Loadable<*>) {
        var hasCallbackBeenRun = false
        loadable.ifLoaded { hasCallbackBeenRun = true }
        if (expected) assertTrue(hasCallbackBeenRun) else assertFalse(hasCallbackBeenRun)
    }
}
