package com.jeanbarrossilva.loadable.list.flow

import com.jeanbarrossilva.loadable.list.ListLoadable
import com.jeanbarrossilva.loadable.list.ListLoadableScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flow

/** Returns a [Flow] containing only non-[loading][ListLoadable.Loading] values. **/
fun <T> Flow<ListLoadable<T>>.filterNotLoading(): Flow<ListLoadable<T>> {
    return filterNot {
        it is ListLoadable.Loading
    }
}

/**
 * Creates a [Flow] of [ListLoadable]s that are emitted through [load] with a [ListLoadableScope]
 * and has an initial [loading][ListLoadable.Loading] value.
 *
 * @param load Operations to be made on the [ListLoadableScope] responsible for emitting
 * [ListLoadable]s sent to it to the created [Flow].
 **/
fun <T> listLoadableFlow(load: suspend ListLoadableScope<T>.() -> Unit): Flow<ListLoadable<T>> {
    return emptyListLoadableFlow {
        load()
        load.invoke(this)
    }
}

/**
 * Creates a [Flow] of [ListLoadable]s that are emitted through [load] with a [ListLoadableScope].
 * Doesn't have any initial value (hence its emptiness).
 *
 * @param load Operations to be made on the [ListLoadableScope] responsible for emitting
 * [ListLoadable]s sent to it to the created [Flow].
 **/
internal fun <T> emptyListLoadableFlow(load: suspend ListLoadableScope<T>.() -> Unit):
    Flow<ListLoadable<T>> {
    return flow<ListLoadable<T>> {
        FlowCollectorListLoadableScope(this).apply {
            load.invoke(this)
        }
    }
        .catch { emit(ListLoadable.Failed(it)) }
}
