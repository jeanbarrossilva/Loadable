package com.jeanbarrossilva.loadable

import com.jeanbarrossilva.loadable.list.serializableListOf
import com.jeanbarrossilva.loadable.list.serialize
import org.junit.Assert.assertEquals
import kotlin.test.Test

internal class CollectionExtensionsTests {
    @Test
    fun `GIVEN a Collection WHEN serializing it THEN it's a SerializableList with all of its previous elements`() {
        assertEquals(serializableListOf(1, 2, 3), listOf(1, 2, 3).serialize())
    }
}
