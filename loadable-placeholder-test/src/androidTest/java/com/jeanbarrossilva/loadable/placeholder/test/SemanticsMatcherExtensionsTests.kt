package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

internal class SemanticsMatcherExtensionsTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun nodeIsLoadingWhenPropertyIsNotSet() {
        composeRule.setContent { Box(Modifier.tagAsPlaceholder()) }
        composeRule.onPlaceholder().assert(isNotLoading())
    }

    @Test
    fun nodeIsLoadingWhenPropertyIsSetToTrue() {
        composeRule.setContent {
            Box(
                Modifier
                    .semantics { set(SemanticsProperties.Loading, true) }
                    .tagAsPlaceholder()
            )
        }
        composeRule.onPlaceholder().assert(isLoading())
    }

    @Test
    fun nodeIsNotLoadingWhenPropertyIsSetToFalse() {
        composeRule.setContent {
            Box(
                Modifier
                    .semantics { set(SemanticsProperties.Loading, false) }
                    .tagAsPlaceholder()
            )
        }
        composeRule.onPlaceholder().assert(isNotLoading())
    }
}
