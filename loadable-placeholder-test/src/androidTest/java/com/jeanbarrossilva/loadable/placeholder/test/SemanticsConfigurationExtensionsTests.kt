package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.semantics.SemanticsConfiguration
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

internal class SemanticsConfigurationExtensionsTests {
    @get:Rule
    val composeRule = createComposeRule()

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
