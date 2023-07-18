package com.jeanbarrossilva.loadable.list.flow

import com.jeanbarrossilva.loadable.list.ListLoadable
import com.jeanbarrossilva.loadable.list.ListLoadableScope
import java.io.Serializable
import kotlinx.coroutines.flow.FlowCollector

/**
 * [ListLoadableScope] that emits sent [ListLoadable]s to the given [collector].
 *
 * @param collector [FlowCollector] to which sent [ListLoadable]s will be emitted.
 **/
internal class FlowCollectorListLoadableScope<T : Serializable?>(
    private val collector: FlowCollector<ListLoadable<T>>
) : ListLoadableScope<T>() {
    override suspend fun send(listLoadable: ListLoadable<T>) {
        collector.emit(listLoadable)
    }
}
