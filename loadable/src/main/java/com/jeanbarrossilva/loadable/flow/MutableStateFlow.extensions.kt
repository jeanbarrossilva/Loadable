package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
import com.jeanbarrossilva.loadable.Serializability
import java.io.NotSerializableException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

/**
 * Maps each emission made to this [Flow] to a [Loadable].
 *
 * Emits, initially, [Loadable.Loading], [Loadable.Loaded] for each value and [Loadable.Failed] for
 * thrown [Throwable]s.
 *
 * **NOTE**: Emitting a value that cannot be serialized to the resulting [Flow] and performing a
 * terminal operation on it will result while [serializability] is
 * [enforced][Serializability.ENFORCED] in a [NotSerializableException] being thrown.
 *
 * @param coroutineScope [CoroutineScope] in which the resulting [MutableStateFlow] will be started
 * and its [value][MutableStateFlow.value] will be shared.
 * @param serializability Determines whether the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content] emitted to to the created [MutableStateFlow] should be
 * serializable.
 **/
fun <T> Flow<T>.loadable(
    coroutineScope: CoroutineScope,
    serializability: Serializability = Serializability.default
): MutableStateFlow<Loadable<T>> {
    return loadableFlow(coroutineScope, serializability) {
        collect(::load)
    }
}

/**
 * Creates a [MutableStateFlow] of [Loadable]s that's started and shared in the [coroutineScope] and
 * emits them through [load] with a [LoadableScope]. Its initial [value][MutableStateFlow.value] is
 * [loading][Loadable.Loading].
 *
 * @param coroutineScope [CoroutineScope] in which the resulting [MutableStateFlow] will be started
 * and its [value][MutableStateFlow.value] will be shared.
 * @param serializability Determines whether the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content] emitted to to the created [MutableStateFlow] should be
 * serializable.
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [MutableStateFlow].
 **/
fun <T> loadableFlow(
    coroutineScope: CoroutineScope,
    serializability: Serializability = Serializability.default,
    load: suspend LoadableScope<T>.() -> Unit
): MutableStateFlow<Loadable<T>> {
    return loadableFlow<T>().apply {
        coroutineScope.launch {
            emitAll(emptyLoadableFlow(serializability, load))
        }
    }
}

/** Creates a [MutableStateFlow] with a [Loadable.Loading] as its initial value. **/
fun <T> loadableFlow(): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loading())
}

/**
 * Creates a [MutableStateFlow] with a [Loadable.Loaded] that wraps the given [content].
 *
 * @param serializability Determines whether the [content] should be serializable.
 * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED]
 * and the [content] cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> loadableFlowOf(content: T, serializability: Serializability = Serializability.default):
    MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loaded(content, serializability))
}
