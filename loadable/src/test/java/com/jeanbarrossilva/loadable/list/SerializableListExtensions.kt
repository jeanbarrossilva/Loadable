package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.list.serializable.SerializableList
import com.jeanbarrossilva.loadable.list.serializable.emptySerializableList
import com.jeanbarrossilva.loadable.list.serializable.serializableListOf
import kotlin.test.Test
import kotlin.test.assertIs

internal class SerializableListExtensions {
    @Test
    fun `GIVEN a loading Loadable WHEN converting it into a ListLoadable THEN it's loading`() {
        assertIs<ListLoadable.Loading<SerializableList<Int>>>(
            Loadable.Loading<SerializableList<Int>>().asListLoadable()
        )
    }

    @Test
    fun `GIVEN a loaded Loadable with an empty list WHEN converting it into a ListLoadable THEN it's empty`() { // ktlint-disable max-line-length
        assertIs<ListLoadable.Empty<SerializableList<Int>>>(
            Loadable.Loaded(emptySerializableList<Int>()).asListLoadable()
        )
    }

    @Test
    fun `GIVEN a loaded Loadable with a populated list WHEN converting it into a ListLoadable THEN it's populated`() { // ktlint-disable max-line-length
        assertIs<ListLoadable.Populated<SerializableList<Int>>>(
            Loadable.Loaded(serializableListOf(1, 2, 3)).asListLoadable()
        )
    }
}
