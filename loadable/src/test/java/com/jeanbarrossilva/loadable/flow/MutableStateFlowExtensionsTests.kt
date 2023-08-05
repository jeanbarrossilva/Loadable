package com.jeanbarrossilva.loadable.flow

import app.cash.turbine.test
import com.jeanbarrossilva.loadable.Loadable
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import org.junit.Assert

internal class MutableStateFlowExtensionsTests {
    @Test
    fun `GIVEN a Loadable Flow with an initial content WHEN collecting it THEN the value that's emitted first is a Loaded one`() { // ktlint-disable max-line-length
        runTest {
            loadableFlowOf(0).test {
                Assert.assertEquals(Loadable.Loaded(0), awaitItem())
            }
        }
    }
}
