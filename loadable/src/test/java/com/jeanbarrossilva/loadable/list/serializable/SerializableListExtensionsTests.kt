package com.jeanbarrossilva.loadable.list.serializable

import java.io.Serializable
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class SerializableListExtensionsTests {
    @Test
    fun `GIVEN a SerializableList created through emptySerializableList WHEN checking if it's empty THEN it is`() { // ktlint-disable max-line-length
        assertContentEquals(emptyList(), emptySerializableList<Serializable>())
    }

    @Test
    fun `GIVEN a SerializableList created through serializableListOf WHEN iterating through it THEN all the given elements are present`() { // ktlint-disable max-line-length
        assertContentEquals(listOf("A", "B", "C"), serializableListOf("A", "B", "C"))
    }
}
