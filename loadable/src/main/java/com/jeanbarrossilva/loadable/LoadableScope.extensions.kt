package com.jeanbarrossilva.loadable

/**
 * Creates a [LoadableScope] with [send] as its [LoadableScope.send] callback.
 *
 * @param send Callback run whenever a [Loadable] is sent.
 **/
internal fun <T> LoadableScope(send: (Loadable<T>) -> Unit): LoadableScope<T> {
    return object : LoadableScope<T>() {
        override suspend fun send(loadable: Loadable<T>) {
            send(loadable)
        }
    }
}
