package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import java.io.NotSerializableException
import kotlinx.coroutines.flow.FlowCollector

/**
 * Emits the given [content] as a [Loadable.Loaded].
 *
 * @param content Element to be emitted.
 * @throws NotSerializableException If the [content] cannot be serialized.
 **/
@Throws(NotSerializableException::class)
internal suspend fun <T> FlowCollector<Loadable<T>>.emit(content: T) {
    emit(Loadable.Loaded(content))
}
