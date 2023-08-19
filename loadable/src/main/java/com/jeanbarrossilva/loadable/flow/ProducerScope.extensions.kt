package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import java.io.NotSerializableException
import kotlinx.coroutines.channels.ProducerScope

/**
 * Sends the given [content] as a [Loadable.Loaded].
 *
 * @param content Element to be sent.
 * @throws NotSerializableException If the [content] cannot be serialized.
 **/
@Throws(NotSerializableException::class)
suspend fun <T> ProducerScope<Loadable<T>>.send(content: T) {
    send(Loadable.Loaded(content))
}
