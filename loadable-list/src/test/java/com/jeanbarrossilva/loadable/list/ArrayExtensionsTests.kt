package com.jeanbarrossilva.loadable.list

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ArrayExtensionsTests {
    @Test
    fun `GIVEN an Array WHEN converting it into a SerializableList THEN it preserves its elements`() { // ktlint-disable max-line-length
        assertEquals(serializableListOf(1, 2, 3), arrayOf(1, 2, 3).toSerializableList())
    }
}
