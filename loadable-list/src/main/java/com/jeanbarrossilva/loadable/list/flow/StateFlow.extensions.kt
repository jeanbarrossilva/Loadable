package com.jeanbarrossilva.loadable.list.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.list.ListLoadable
import com.jeanbarrossilva.loadable.list.ListLoadableScope
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.toListLoadable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Maps each emission to a [ListLoadable] and emits an initial [loading][ListLoadable.Loading]
 * value.
 *
 * @param coroutineScope [CoroutineScope] in which the [StateFlow] will be started and its
 * [value][StateFlow.value] will be shared.
 * @param sharingStarted Strategy for controlling when sharing starts and ends.
 **/
fun <T> Flow<SerializableList<T>>.listLoadable(
    coroutineScope: CoroutineScope,
    sharingStarted: SharingStarted
): StateFlow<ListLoadable<T>> {
    return loadable().map(Loadable<SerializableList<T>>::toListLoadable).stateIn(
        coroutineScope,
        sharingStarted,
        initialValue = ListLoadable.Loading()
    )
}

/**
 * Creates a [StateFlow] of [ListLoadable]s that's started and shared in the [coroutineScope] and
 * emits them through [load] with a [ListLoadableScope]. Its initial [value][StateFlow.value] is
 * [loading][ListLoadable.Loading].
 *
 * @param coroutineScope [CoroutineScope] in which the resulting [StateFlow] will be started and its
 * value will be shared.
 * @param load Operations to be made on the [ListLoadableScope] responsible for emitting
 * [ListLoadable]s sent to it to the created [StateFlow].
 **/
fun <T> listLoadableFlow(
    coroutineScope: CoroutineScope,
    load: suspend ListLoadableScope<T>.() -> Unit
): StateFlow<ListLoadable<T>> {
    return listLoadableFlow<T>()
        .apply {
            coroutineScope.launch {
                emitAll(emptyListLoadableFlow(load))
            }
        }
        .asStateFlow()
}
