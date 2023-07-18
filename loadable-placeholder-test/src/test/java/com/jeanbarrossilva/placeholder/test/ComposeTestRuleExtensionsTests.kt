package com.jeanbarrossilva.placeholder.test

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import com.jeanbarrossilva.loadable.placeholder.test.onPlaceholder
import com.jeanbarrossilva.loadable.placeholder.test.tagAsPlaceholder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ComposeTestRuleExtensionsTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun getsPlaceholderNode() {
        composeRule.setContent { Box(Modifier.tagAsPlaceholder()) }
        composeRule.onPlaceholder().assertExists()
    }
}
