package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable
import kotlinx.coroutines.flow.MutableStateFlow

/** Creates a [MutableStateFlow] with a [Loadable.Loading] as its initial value. **/
internal fun <T : Serializable?> loadableFlow(): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loading())
}

/** Creates a [MutableStateFlow] with a [Loadable.Loaded] that wraps the given [content]. **/
internal fun <T : Serializable?> loadableFlowOf(content: T): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loaded(content))
}
