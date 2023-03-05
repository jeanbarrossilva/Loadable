package com.jeanbarrossilva.loadable

import com.jeanbarrossilva.loadable.utils.serializableListOf
import com.jeanbarrossilva.loadable.utils.serialize
import kotlin.test.Test
import org.junit.Assert.assertEquals

internal class CollectionExtensionsTests {
    @Test
    fun `GIVEN a Collection WHEN serializing it THEN it's a SerializableList with all of its previous elements`() {
        assertEquals(serializableListOf(1, 2, 3), listOf(1, 2, 3).serialize())
    }
}
