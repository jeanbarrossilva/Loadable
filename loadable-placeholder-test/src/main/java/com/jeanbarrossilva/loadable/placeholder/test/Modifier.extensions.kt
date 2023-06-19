package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag

/**
 * Shorthand for test-tagging as [PLACEHOLDER_TAG].
 *
 * @see testTag
 **/
fun Modifier.tagAsPlaceholder(): Modifier {
    return testTag(PLACEHOLDER_TAG)
}
