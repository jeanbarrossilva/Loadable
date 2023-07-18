package com.jeanbarrossilva.placeholder.test

import androidx.compose.ui.semantics.SemanticsProperties
import com.jeanbarrossilva.loadable.placeholder.test.Loading
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class SemanticsPropertiesExtensionsTests {
    @Test
    fun loadingPropertyHasASingleInstance() {
        assertEquals(SemanticsProperties.Loading, SemanticsProperties.Loading)
    }
}
