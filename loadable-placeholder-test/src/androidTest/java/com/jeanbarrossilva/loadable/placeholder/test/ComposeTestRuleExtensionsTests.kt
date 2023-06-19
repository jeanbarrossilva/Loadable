package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import org.junit.Rule
import org.junit.Test

internal class ComposeTestRuleExtensionsTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun getsPlaceholderNode() {
        composeRule.setContent { Box(Modifier.tagAsPlaceholder()) }
        composeRule.onPlaceholder().assertExists()
    }
}
