package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import kotlinx.coroutines.flow.FlowCollector
import java.io.Serializable

/**
 * Emits the given [element] as a [Loadable.Loaded].
 *
 * @param element Element to be emitted.
 **/
internal suspend fun <T : Serializable?> FlowCollector<Loadable<T>>.emit(element: T) {
    emit(Loadable.Loaded(element))
}
