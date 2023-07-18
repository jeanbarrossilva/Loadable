package com.jeanbarrossilva.loadable.list

import kotlin.test.Test
import kotlin.test.assertEquals

internal class CollectionExtensionsTests {
    @Test
    fun `GIVEN a Collection WHEN converting it into a SerializableList THEN it preserves its previous elements`() { // ktlint-disable max-line-length
        assertEquals(serializableListOf(1, 2, 3), listOf(1, 2, 3).toSerializableList())
    }
}
