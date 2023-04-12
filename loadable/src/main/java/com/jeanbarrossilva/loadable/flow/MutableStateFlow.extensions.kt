package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import kotlinx.coroutines.flow.MutableStateFlow
import java.io.Serializable

/** Creates a [MutableStateFlow] with a [Loadable.Loading] as its initial value. **/
internal fun <T : Serializable?> loadable(): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loading())
}

/** Creates a [MutableStateFlow] with a [Loadable.Loaded] that wraps the given [value]. **/
internal fun <T : Serializable?> loadable(value: T): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loaded(value))
}
