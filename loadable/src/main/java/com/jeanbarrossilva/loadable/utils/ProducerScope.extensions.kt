package com.jeanbarrossilva.loadable.utils

import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable
import kotlinx.coroutines.channels.ProducerScope

/**
 * Sends the given [element] as a [Loadable.Loaded].
 *
 * @param element Element to be sent.
 **/
suspend fun <T : Serializable?> ProducerScope<Loadable<T>>.send(element: T) {
    send(Loadable.Loaded(element))
}
