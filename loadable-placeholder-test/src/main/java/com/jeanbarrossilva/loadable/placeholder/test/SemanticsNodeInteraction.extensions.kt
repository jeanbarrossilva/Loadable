package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert

/**
 * Asserts that the node is loading.
 *
 * @see isLoading
 **/
fun SemanticsNodeInteraction.assertIsLoading(): SemanticsNodeInteraction {
    return assert(isLoading())
}

/**
 * Asserts that the node is not loading.
 *
 * @see isNotLoading
 **/
fun SemanticsNodeInteraction.assertIsNotLoading(): SemanticsNodeInteraction {
    return assert(isNotLoading())
}
