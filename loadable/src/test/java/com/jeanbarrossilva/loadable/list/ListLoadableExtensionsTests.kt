package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull

internal class ListLoadableExtensionsTests {
    @Test
    fun `GIVEN a loading ListLoadable WHEN trying to find an element THEN it's also loading`() {
        assertIs<Loadable.Loading<Int>>(ListLoadable.Loading<Int>().find { true })
    }

    @Test
    fun `GIVEN an empty ListLoadable WHEN trying to find an element THEN it's null`() {
        assertEquals(Loadable.Loaded<Int?>(null), ListLoadable.Empty<Int>().find { true })
    }

    @Test
    fun `GIVEN a populated ListLoadable WHEN trying to find an existing element THEN it's found`() {
        assertEquals(
            Loadable.Loaded<Int?>(2),
            ListLoadable.Populated(serializableListOf(1, 2, 3)).find { number -> number % 2 == 0 }
        )
    }

    @Test
    fun `GIVEN a failed ListLoadable WHEN trying to find an element THEN it's also failed`() {
        assertIs<Loadable.Failed<Int>>(ListLoadable.Failed<Int>(Exception()).find { true })
    }

    @Test
    fun `GIVEN a loading ListLoadable WHEN returning a value if it's populated THEN it returns null`() { // ktlint-disable max-line-length
        assertNull(ListLoadable.Loading<Int>().ifPopulated { 0 })
    }

    @Test
    fun `GIVEN an empty ListLoadable WHEN returning a value if it's populated THEN it returns null`() { // ktlint-disable max-line-length
        assertNull(ListLoadable.Empty<Int>().ifPopulated { 0 })
    }

    @Test
    fun `GIVEN a populated ListLoadable WHEN returning a value if it's populated THEN it returns the value`() { // ktlint-disable max-line-length
        assertEquals(0, ListLoadable.Populated(serializableListOf(1, 2, 3)).ifPopulated { 0 })
    }

    @Test
    fun `GIVEN a failed ListLoadable WHEN returning a value if it's populated THEN it returns null`() { // ktlint-disable max-line-length
        assertNull(ListLoadable.Empty<Int>().ifPopulated { 0 })
    }

    @Test
    fun `GIVEN a loading ListLoadable WHEN mapping-not-null THEN it's loading`() {
        assertIs<ListLoadable.Loading<Int>>(ListLoadable.Loading<Int>().mapNotNull { 0 })
    }

    @Test
    fun `GIVEN an empty ListLoadable WHEN mapping-not-null THEN it's empty`() {
        assertIs<ListLoadable.Empty<Int>>(ListLoadable.Empty<Int>().mapNotNull { 0 })
    }

    @Test
    fun `GIVEN a populated ListLoadable WHEN mapping-not-null with null transforms THEN it's empty`() { // ktlint-disable max-line-length
        assertIs<ListLoadable.Empty<Int>>(
            ListLoadable.Populated(serializableListOf(1, 2, 3)).mapNotNull { number ->
                if (number < 1) number else null
            }
        )
    }

    @Test
    fun `GIVEN a populated ListLoadable WHEN mapping-not-null with non-null transforms THEN it's populated`() { // ktlint-disable max-line-length
        assertEquals(
            ListLoadable.Populated(serializableListOf(2)),
            ListLoadable.Populated(serializableListOf(1, 2, 3)).mapNotNull { number ->
                if (number % 2 == 0) number else null
            }
        )
    }

    @Test
    fun `GIVEN a failed ListLoadable WHEN mapping-not-null THEN it's failed`() {
        assertIs<ListLoadable.Failed<Int>>(ListLoadable.Failed<Int>(Exception()).mapNotNull { 0 })
    }
}
