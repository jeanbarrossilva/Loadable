package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import kotlinx.coroutines.channels.ProducerScope

/**
 * Sends the given [element] as a [Loadable.Loaded].
 *
 * @param element Element to be sent.
 **/
suspend fun <T> ProducerScope<Loadable<T>>.send(element: T) {
    send(Loadable.Loaded(element))
}
