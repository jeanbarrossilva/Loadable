package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

internal class SemanticsNodeInteractionExtensionsTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun doesNotThrowWhenAssertingThatANodeIsLoadingAndItIs() {
        composeRule.setContent {
            Box(
                Modifier
                    .semantics { set(SemanticsProperties.Loading, true) }
                    .tagAsPlaceholder()
            )
        }
        composeRule.onPlaceholder().assertIsLoading()
    }

    @Test(expected = AssertionError::class)
    fun throwsWhenAssertingThatANodeIsLoadingAndItIsNot() {
        composeRule.setContent { Box(Modifier.tagAsPlaceholder()) }
        composeRule.onPlaceholder().assertIsLoading()
    }

    @Test
    fun doesNotThrowWhenAssertingThatANodeIsNotLoadingAndItIsNot() {
        composeRule.setContent { Box(Modifier.tagAsPlaceholder()) }
        composeRule.onPlaceholder().assertIsNotLoading()
    }

    @Test(expected = AssertionError::class)
    fun throwsWhenAssertingThatANodeIsNotLoadingAndItIs() {
        composeRule.setContent {
            Box(
                Modifier
                    .semantics { set(SemanticsProperties.Loading, true) }
                    .tagAsPlaceholder()
            )
        }
        composeRule.onPlaceholder().assertIsNotLoading()
    }
}
