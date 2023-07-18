package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createComposeRule
import com.jeanbarrossilva.loadable.placeholder.test.onPlaceholder
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
    fun isNotTransformedWhenConditionPassedToIfIsFalse() {
        composeRule.setContent { Placeholder(Modifier.`if`(false, Modifier::tagAsPlaceholder)) }
        composeRule.onPlaceholder().assertDoesNotExist()
    }

    @Test
    fun isTransformedWhenConditionPassedToIfIsTrue() {
        composeRule.setContent { Placeholder(Modifier.`if`(true, Modifier::tagAsPlaceholder)) }
        composeRule.onPlaceholder().assertExists()
    }

    @Test
    fun transformIsAppliedToReceiverModifierOfIf() {
        composeRule.setContent {
            Placeholder(
                Modifier
                    .semantics { set(SemanticsProperties.ToggleableState, ToggleableState.On) }
                    .`if`(true, Modifier::tagAsPlaceholder)
            )
        }
        composeRule.onPlaceholder().assertIsOn()
    }
}
