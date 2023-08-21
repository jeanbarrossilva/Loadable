package com.jeanbarrossilva.loadable

/**
 * Creates a [LoadableScope] with [send] as its [LoadableScope.send] callback.
 *
 * @param serializability Determines whether loaded content should be serializable.
 * @param send Callback run whenever a [Loadable] is sent.
 **/
internal fun <T> LoadableScope(
    serializability: Serializability = Serializability.default,
    send: (Loadable<T>) -> Unit
): LoadableScope<T> {
    return object : LoadableScope<T>() {
        override val serializability = serializability

        override suspend fun send(loadable: Loadable<T>) {
            send(loadable)
        }
    }
}
