package com.jeanbarrossilva.loadable

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.Serializable
import kotlin.test.assertIs

internal class LoadableScopeTests {
    private val sent = mutableListOf<Loadable<Serializable?>>()
    private val scope: LoadableScope<Serializable?> = LoadableScope(sent::add)

    @After
    fun tearDown() {
        sent.clear()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `GIVEN a Loading Loadable WHEN sending it to the scope THEN it's received`() {
        runTest { scope.load() }
        assertIs<Loadable.Loading<Serializable?>>(sent.single())
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `GIVEN a Loaded Loadable WHEN sending it to the scope THEN it's received`() {
        runTest { scope.load(null) }
        assertEquals(Loadable.Loaded(null), sent.single())
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun `GIVEN a Failed Loadable WHEN sending it to the scope THEN it's received`() {
        runTest { scope.fail(NullPointerException()) }
        sent.single().let {
            assertIs<Loadable.Failed<Serializable?>>(it)
            assertIs<NullPointerException>(it.error)
        }
    }
}
