package com.jeanbarrossilva.loadable.list.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.flow.loadable
import com.jeanbarrossilva.loadable.list.ListLoadable
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.toListLoadable
import java.io.Serializable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/** Returns a [Flow] containing only non-[loading][ListLoadable.Loading] values. **/
fun <T : Serializable?> Flow<ListLoadable<T>>.filterNotLoading(): Flow<ListLoadable<T>> {
    return filterNot {
        it is ListLoadable.Loading
    }
}

/**
 * Maps each emission to a [ListLoadable] and emits an initial [loading][ListLoadable.Loading]
 * value.
 *
 * @param coroutineScope [CoroutineScope] in which the [StateFlow] will be started and its
 * [value][StateFlow.value] will be shared.
 * @param sharingStarted Strategy for controlling when sharing starts and ends.
 **/
fun <T : Serializable?> Flow<SerializableList<T>>.listLoadable(
    coroutineScope: CoroutineScope,
    sharingStarted: SharingStarted
): StateFlow<ListLoadable<T>> {
    return loadable().map(Loadable<SerializableList<T>>::toListLoadable).stateIn(
        coroutineScope,
        sharingStarted,
        initialValue = ListLoadable.Loading()
    )
}
