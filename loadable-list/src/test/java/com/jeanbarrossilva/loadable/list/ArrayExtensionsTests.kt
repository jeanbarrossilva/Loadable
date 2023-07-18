package com.jeanbarrossilva.loadable.list

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ArrayExtensionsTests {
    @Test
    fun `GIVEN an Array WHEN serializing it THEN it's a SerializableList with all of its previous elements`() { // ktlint-disable max-line-length
        assertEquals(serializableListOf(1, 2, 3), arrayOf(1, 2, 3).serialize())
    }
}
