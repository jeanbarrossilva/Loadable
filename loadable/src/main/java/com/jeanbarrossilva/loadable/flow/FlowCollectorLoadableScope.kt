package com.jeanbarrossilva.loadable.flow

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
import java.io.Serializable
import kotlinx.coroutines.flow.FlowCollector

/**
 * [LoadableScope] that emits sent [Loadable]s to the given [collector].
 *
 * @param collector [FlowCollector] to which sent [Loadable]s will be emitted.
 **/
@PublishedApi
internal class FlowCollectorLoadableScope<T : Serializable?>(
    private val collector: FlowCollector<Loadable<T>>
) : LoadableScope<T>() {
    override suspend fun send(loadable: Loadable<T>) {
        collector.emit(loadable)
    }
}
