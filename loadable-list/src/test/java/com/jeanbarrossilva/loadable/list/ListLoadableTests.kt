package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class ListLoadableTests {
    @Test
    fun `GIVEN a loading ListLoadable WHEN converting it into a Loadable THEN it's loading`() {
        assertIs<Loadable.Loading<Int>>(ListLoadable.Loading<Int>().toLoadable())
    }

    @Test
    fun `GIVEN an empty ListLoadable WHEN converting it into a Loadable THEN it's a loaded one with an empty ListLoadable as its content`() { // ktlint-disable max-line-length
        assertEquals(
            Loadable.Loaded(emptySerializableList()),
            ListLoadable.Empty<Int>().toLoadable()
        )
    }

    @Test
    fun `GIVEN a populated ListLoadable WHEN converting it into a Loadable THEN it's a loaded one with the equivalent list as its content`() { // ktlint-disable max-line-length
        assertEquals(
            Loadable.Loaded(serializableListOf(1, 2, 3)),
            ListLoadable.Populated(serializableListOf(1, 2, 3)).toLoadable()
        )
    }

    @Test
    fun `GIVEN a failed ListLoadable WHEN converting it into a Loadable THEN it's failed`() {
        assertIs<Loadable.Failed<Int>>(ListLoadable.Failed<Int>(Exception()).toLoadable())
    }
}
