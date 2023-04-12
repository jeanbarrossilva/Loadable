package com.jeanbarrossilva.loadable.list

import java.io.Serializable
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class SerializableListExtensionsTests {
    @Test
    fun `GIVEN a SerializableList created through emptySerializableList WHEN checking if it's empty THEN it is`() {
        assertContentEquals(emptyList(), emptySerializableList<Serializable>())
    }

    @Test
    fun `GIVEN a SerializableList created through serializableListOf WHEN iterating through it THEN all the given elements are present`() {
        assertContentEquals(listOf("A", "B", "C"), serializableListOf("A", "B", "C"))
    }
}
