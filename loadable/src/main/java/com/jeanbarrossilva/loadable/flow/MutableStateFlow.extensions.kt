package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
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
 * @param coroutineScope [CoroutineScope] in which the resulting [MutableStateFlow] will be started
 * and its [value][MutableStateFlow.value] will be shared.
 **/
fun <T> Flow<T>.loadable(coroutineScope: CoroutineScope): MutableStateFlow<Loadable<T>> {
    return loadableFlow(coroutineScope) {
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
 * @param load Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [MutableStateFlow].
 **/
fun <T> loadableFlow(coroutineScope: CoroutineScope, load: suspend LoadableScope<T>.() -> Unit):
    MutableStateFlow<Loadable<T>> {
    return loadableFlow<T>().apply {
        coroutineScope.launch {
            emitAll(emptyLoadableFlow(load))
        }
    }
}

/** Creates a [MutableStateFlow] with a [Loadable.Loading] as its initial value. **/
fun <T> loadableFlow(): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loading())
}

/** Creates a [MutableStateFlow] with a [Loadable.Loaded] that wraps the given [content]. **/
fun <T> loadableFlowOf(content: T): MutableStateFlow<Loadable<T>> {
    return MutableStateFlow(Loadable.Loaded(content))
}
