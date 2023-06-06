package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable
import kotlin.test.Test
import kotlin.test.assertContentEquals
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

    @Test
    fun `GIVEN a SerializableList created through emptySerializableList WHEN checking if it's empty THEN it is`() { // ktlint-disable max-line-length
        assertContentEquals(emptyList(), emptySerializableList<Serializable>())
    }

    @Test
    fun `GIVEN a SerializableList created through serializableListOf WHEN iterating through it THEN all the given elements are present`() { // ktlint-disable max-line-length
        assertContentEquals(listOf("A", "B", "C"), serializableListOf("A", "B", "C"))
    }
}
