package com.jeanbarrossilva.loadable

import java.io.Serializable

/**
 * Creates a [LoadableScope] with [send] as its [LoadableScope.send] callback.
 *
 * @param send Callback run whenever a [Loadable] is sent.
 **/
@Suppress("FunctionName")
internal fun <T : Serializable?> LoadableScope(send: (Loadable<T>) -> Unit): LoadableScope<T> {
    return object : LoadableScope<T>() {
        override suspend fun send(loadable: Loadable<T>) {
            send(loadable)
        }
    }
}
