package com.jeanbarrossilva.loadable.placeholder

import androidx.compose.ui.Modifier

/**
 * Returns the result of the given [transform] if the [condition] is `true`; otherwise, returns the
 * receiver [Modifier].
 *
 * @param condition Determines whether the result of [transform] will get returned.
 * @param transform Transformation to be made to this [Modifier].
 **/
internal fun Modifier.`if`(condition: Boolean, transform: Modifier.() -> Modifier): Modifier {
    return if (condition) transform() else this
}
