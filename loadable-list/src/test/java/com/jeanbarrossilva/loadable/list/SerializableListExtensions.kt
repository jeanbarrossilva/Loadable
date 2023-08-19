package com.jeanbarrossilva.loadable.list

import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class SerializableListExtensions {
    @Test
    fun `GIVEN an empty SerializableList WHEN converting it into a ListLoadable THEN it's empty`() {
        assertIs<ListLoadable.Empty<String>>(emptySerializableList<String>().toListLoadable())
    }

    @Test
    fun `GIVEN a populated SerializableList WHEN converting it into a ListLoadable THEN it's populated`() { // ktlint-disable max-line-length
        assertEquals(
            ListLoadable.Populated(serializableListOf("A", "B", "C")),
            serializableListOf("A", "B", "C").toListLoadable()
        )
    }

    @Test
    fun `GIVEN a SerializableList created through emptySerializableList WHEN checking if it's empty THEN it is`() { // ktlint-disable max-line-length
        assertContentEquals(emptyList(), emptySerializableList<Any?>())
    }

    @Test
    fun `GIVEN a SerializableList created through serializableListOf WHEN iterating through it THEN all the given elements are present`() { // ktlint-disable max-line-length
        assertContentEquals(listOf("A", "B", "C"), serializableListOf("A", "B", "C"))
    }
}
