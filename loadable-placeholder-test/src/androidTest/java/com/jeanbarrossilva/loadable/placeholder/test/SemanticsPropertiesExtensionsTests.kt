package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.semantics.SemanticsProperties
import org.junit.Assert.assertEquals
import org.junit.Test

internal class SemanticsPropertiesExtensionsTests {
    @Test
    fun loadingPropertyHasASingleInstance() {
        assertEquals(SemanticsProperties.Loading, SemanticsProperties.Loading)
    }
}
