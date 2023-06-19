package com.jeanbarrossilva.loadable.placeholder.test

import androidx.compose.ui.test.SemanticsMatcher

/** [SemanticsMatcher] that indicates whether the node is loading. **/
fun isLoading(): SemanticsMatcher {
    return SemanticsMatcher("is loading") {
        it.config.isLoading
    }
}

/** [SemanticsMatcher] that indicates whether the node is not loading. **/
fun isNotLoading(): SemanticsMatcher {
    return SemanticsMatcher("is not loading") {
        !it.config.isLoading
    }
}
