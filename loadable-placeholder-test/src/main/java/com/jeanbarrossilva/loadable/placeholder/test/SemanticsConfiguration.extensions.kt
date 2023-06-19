package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.semantics.SemanticsConfiguration
import androidx.compose.ui.semantics.SemanticsProperties

/**
 * Whether [SemanticsProperties.Loading] has been set to `true`. If it's not been set at all,
 * returns `false`.
 **/
internal val SemanticsConfiguration.isLoading
    get() = getOrElse(SemanticsProperties.Loading) { false }
