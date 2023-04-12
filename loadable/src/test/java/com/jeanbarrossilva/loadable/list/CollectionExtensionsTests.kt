package com.jeanbarrossilva.loadable.list

import kotlin.test.Test
import org.junit.Assert.assertEquals

internal class CollectionExtensionsTests {
    @Test
    fun `GIVEN a Collection WHEN serializing it THEN it's a SerializableList with all of its previous elements`() { // ktlint-disable max-line-length
        assertEquals(serializableListOf(1, 2, 3), listOf(1, 2, 3).serialize())
    }
}
