package com.jeanbarrossilva.placeholder.test

import androidx.compose.foundation.layout.Box
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.junit4.createComposeRule
import com.jeanbarrossilva.loadable.placeholder.test.Loading
import com.jeanbarrossilva.loadable.placeholder.test.isLoading
import com.jeanbarrossilva.loadable.placeholder.test.isNotLoading
import com.jeanbarrossilva.loadable.placeholder.test.onPlaceholder
import com.jeanbarrossilva.loadable.placeholder.test.tagAsPlaceholder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
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
