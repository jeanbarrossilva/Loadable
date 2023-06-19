package com.jeanbarrossilva.loadable.placeholder.test

import androidx.annotation.RestrictTo
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag

/** Tag that identifies a placeholder for testing purposes. **/
@RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
const val PLACEHOLDER_TAG = "placeholder"

/**
 * [SemanticsNodeInteraction] of a placeholder, [test-tag][Modifier.testTag]ged as
 * [PLACEHOLDER_TAG].
 **/
fun ComposeTestRule.onPlaceholder(): SemanticsNodeInteraction {
    return onNodeWithTag(PLACEHOLDER_TAG)
}
