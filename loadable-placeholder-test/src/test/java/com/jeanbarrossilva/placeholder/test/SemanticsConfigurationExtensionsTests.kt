package com.jeanbarrossilva.placeholder.test

import androidx.compose.ui.semantics.SemanticsConfiguration
import androidx.compose.ui.semantics.SemanticsProperties
import com.jeanbarrossilva.loadable.placeholder.test.Loading
import com.jeanbarrossilva.loadable.placeholder.test.isLoading
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

internal class SemanticsConfigurationExtensionsTests {
    @Test
    fun isNotLoadingWhenPropertyIsNotSet() {
        val configuration = SemanticsConfiguration()
        assertFalse(configuration.isLoading)
    }

    @Test
    fun isLoadingWhenPropertyIsSetToTrue() {
        val configuration =
            SemanticsConfiguration().apply { set(SemanticsProperties.Loading, true) }
        assertTrue(configuration.isLoading)
    }

    @Test
    fun nodeIsNotLoadingWhenPropertyIsSetToFalse() {
        val configuration =
            SemanticsConfiguration().apply { set(SemanticsProperties.Loading, false) }
        assertFalse(configuration.isLoading)
    }
}
