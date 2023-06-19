package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.semantics.SemanticsPropertyKey

/**
 * [SemanticsPropertyKey] returned by [SemanticsProperties.Loading].
 *
 * Because [SemanticsPropertyKey]
 * isn't a [data class](https://kotlinlang.org/docs/data-classes.html) and overrides neither
 * [equals][SemanticsPropertyKey.equals] nor [hashCode][SemanticsPropertyKey.hashCode], returning
 * this directly in the [SemanticsProperties.Loading] extension would create different instances
 * each time its [getter](https://kotlinlang.org/docs/extensions.html#extension-properties) is
 * invoked.
 **/
private val LoadingSemanticsProperty = SemanticsPropertyKey<Boolean>("Loading")

/** [SemanticsPropertyKey] that indicates whether content is being loaded. **/
@Suppress("UnusedReceiverParameter")
val SemanticsProperties.Loading
    get() = LoadingSemanticsProperty
