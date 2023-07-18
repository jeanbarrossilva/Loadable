package com.jeanbarrossilva.placeholder.test

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.jeanbarrossilva.loadable.placeholder.test.PLACEHOLDER_TAG
import com.jeanbarrossilva.loadable.placeholder.test.tagAsPlaceholder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
internal class ModifierExtensionsTests {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun tagsAsPlaceholder() {
        composeRule.setContent { Box(Modifier.tagAsPlaceholder()) }
        composeRule.onNodeWithTag(PLACEHOLDER_TAG).assertExists()
    }
}
