package com.jeanbarrossilva.loadable.flow

import app.cash.turbine.test
import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable
import kotlin.test.Test
import kotlin.test.assertIs
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals

internal class FlowExtensionsTests {
    @Test
    fun `GIVEN a Loadable Flow built through a scope WHEN loading THEN a Loading has been emitted`() { // ktlint-disable max-line-length
        runTest {
            loadableFlow<Serializable?> { load() }.test {
                repeat(2) { assertIs<Loadable.Loading<Serializable?>>(awaitItem()) }
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow built through a scope WHEN loaded THEN a Loaded has been emitted`() {
        runTest {
            loadableFlow<Serializable?> { load(null) }.unwrap().test {
                assertEquals(null, awaitItem())
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow built through a scope WHEN failed THEN a Failed has been emitted`() {
        runTest {
            loadableFlow<Serializable?> { fail(NullPointerException()) }.filterIsFailed().test {
                awaitItem()
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow without an initial content WHEN collecting it THEN the value that's emitted first is a Loading one`() { // ktlint-disable max-line-length
        runTest {
            loadableFlow<Serializable>().test {
                assertIs<Loadable.Loading<Serializable>>(awaitItem())
            }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow WHEN filtering Failed values THEN they're all emitted`() {
        runTest {
            flow {
                emit(9)
                null!!
            }
                .loadable()
                .filterIsFailed()
                .test {
                    assertIs<NullPointerException>(awaitItem().error)
                    awaitComplete()
                }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow WHEN filtering Loaded values THEN they're all emitted`() {
        runTest {
            loadableFlow {
                load("Getting to it...")
                load()
                fail(Throwable())
                load("Failed. Trying again...")
                load("Almost there...")
                load("Done!")
            }
                .filterIsLoaded()
                .test {
                    repeat(4) { awaitItem() }
                    awaitComplete()
                }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow WHEN inner-mapping it THEN the emitted values are transformed`() {
        runTest {
            loadableFlow {
                load(1)
                load(2)
                load(3)
            }
                .innerMap(2::times)
                .unwrap()
                .test {
                    assertEquals(2, awaitItem())
                    assertEquals(4, awaitItem())
                    assertEquals(6, awaitItem())
                    awaitComplete()
                }
        }
    }

    @Test
    fun `GIVEN a Flow WHEN converting it into a Loadable one THEN the value that's emitted first is a Loaded one`() { // ktlint-disable max-line-length
        runTest {
            flow { emit(true) }.loadable().test {
                assertIs<Loadable.Loading<Boolean>>(awaitItem())
                awaitItem()
                awaitComplete()
            }
        }
    }

    @Test
    fun `GIVEN a Flow WHEN converting it into a Loadable one THEN its values are emitted as Loaded`() { // ktlint-disable max-line-length
        runTest {
            flow {
                emit("Hello, ")
                emit("world")
                emit("!")
            }
                .loadable()
                .test {
                    awaitItem()
                    repeat(3) { assertIs<Loadable.Loaded<String>>(awaitItem()) }
                    awaitComplete()
                }
        }
    }

    @Suppress("DIVISION_BY_ZERO")
    @Test
    fun `GIVEN a Flow WHEN converting it into a Loadable one THEN thrown exceptions are emitted as Failed`() { // ktlint-disable max-line-length
        runTest {
            flow<Serializable> { 0 / 0 }
                .loadable()
                .filterIsFailed()
                .test {
                    assertIs<ArithmeticException>(awaitItem().error)
                    awaitComplete()
                }
        }
    }

    @Test
    fun `GIVEN a Flow WHEN converting it into a Loadable one in a CoroutineScope THEN Loading is only emitted once as an initial value`() { // ktlint-disable max-line-length
        runTest {
            flowOf(0).loadable().test {
                assertIs<Loadable.Loading<Int>>(awaitItem())
                assertEquals(Loadable.Loaded(0), awaitItem())
                awaitComplete()
            }
        }
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `GIVEN a Loadable Flow WHEN unwrapping it THEN only Loaded Loadables' contents are emitted`() { // ktlint-disable max-line-length
        runTest {
            loadableFlow {
                load(8)
                fail(Throwable())
                load()
                load(16)
            }
                .unwrap()
                .test {
                    assertEquals(8, awaitItem())
                    assertEquals(16, awaitItem())
                    awaitComplete()
                }
        }
    }

    @Test
    fun `GIVEN a Loadable Flow WHEN unwrapping its content THEN only Loaded values with non-null content are emitted`() { // ktlint-disable max-line-length
        runTest {
            emptyLoadableFlow {
                load(null)
                load(0)
                load(null)
                load(1)
            }
                .unwrapContent()
                .test {
                    assertEquals(Loadable.Loaded(0), awaitItem())
                    assertEquals(Loadable.Loaded(1), awaitItem())
                    awaitComplete()
                }
        }
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `GIVEN a Loadable Flow that's Loading WHEN sending its Loadables into a LoadableScope THEN it loads`() { // ktlint-disable max-line-length
        runTest {
            emptyLoadableFlow(loadableFlow<Serializable?>()::sendTo).test {
                assertIs<Loadable.Loading<Serializable?>>(awaitItem())
            }
        }
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `GIVEN a Loadable Flow that's Loaded WHEN sending its Loadables into a LoadableScope THEN it loads the content`() { // ktlint-disable max-line-length
        runTest {
            loadableFlow(loadableFlowOf(0)::sendTo).test {
                awaitItem()
                assertEquals(Loadable.Loaded(0), awaitItem())
            }
        }
    }

    @Suppress("SpellCheckingInspection")
    @Test
    fun `GIVEN a Loadable Flow that's Failed WHEN sending its Loadables into a LoadableScope THEN it fails`() { // ktlint-disable max-line-length
        val error = Throwable()
        runTest {
            loadableFlow {
                emptyLoadableFlow<Serializable?> { fail(error) }.sendTo(this)
            }
                .test {
                    awaitItem()
                    assertEquals(Loadable.Failed<Serializable?>(error), awaitItem())
                    awaitComplete()
                }
        }
    }

    @Test
    fun `GIVEN a Flow WHEN loading it to the LoadableScope of a public Loadable-Flow-creator function THEN it's initially Loading once`() { // ktlint-disable max-line-length
        runTest {
            loadableFlow { flowOf(0).loadTo(this) }.test {
                assertIs<Loadable.Loading<Int>>(awaitItem())
                assertEquals(Loadable.Loaded(0), awaitItem())
                awaitComplete()
            }
        }
    }

    @Suppress("CAST_NEVER_SUCCEEDS")
    @Test
    fun `GIVEN a Loadable ChannelFlow WHEN emitting from different scopes THEN it receives sent emissions`() { // ktlint-disable max-line-length
        runTest {
            loadableChannelFlow {
                send(2)
                send(4)
                send(Loadable.Failed(Throwable()))
                send(Loadable.Loading())
                send(8)
                "String" as Int
            }
                .test {
                    assertIs<Loadable.Loading<Int>>(awaitItem())
                    assertEquals(Loadable.Loaded(2), awaitItem())
                    assertEquals(Loadable.Loaded(4), awaitItem())
                    assertIs<Loadable.Failed<Int>>(awaitItem())
                    assertIs<Loadable.Loading<Int>>(awaitItem())
                    assertEquals(Loadable.Loaded(8), awaitItem())
                    awaitItem().let {
                        assertIs<Loadable.Failed<Int>>(it)
                        assertIs<ClassCastException>(it.error)
                    }
                    awaitComplete()
                }
        }
    }
}
