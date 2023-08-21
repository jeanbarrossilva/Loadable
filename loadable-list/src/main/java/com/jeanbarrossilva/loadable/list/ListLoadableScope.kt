package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Serializability
import java.io.NotSerializableException

/** Scope through which [ListLoadable]s are sent. **/
abstract class ListLoadableScope<T> internal constructor() {
    /** Sends a [ListLoadable.Loading]. **/
    suspend fun load() {
        send(ListLoadable.Loading())
    }

    /**
     * Sends a [ListLoadable] that matches the given [content].
     *
     * @param content [Array] to be converted into a [SerializableList] and sent either as a
     * [ListLoadable.Empty] or a [ListLoadable.Populated].
     * @param serializability Determines whether each [content] element should be serializable.
     * @see Array.toSerializableList
     * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED]
     * and any of the [content]'s elements cannot be serialized.
     **/
    @Throws(NotSerializableException::class)
    suspend fun load(
        vararg content: T,
        serializability: Serializability = Serializability.IGNORED
    ) {
        load(content.toSerializableList(serializability))
    }

    /**
     * Sends a [ListLoadable] that matches the given [content].
     *
     * @param content [SerializableList] to be sent either as a [ListLoadable.Empty] or a
     * [ListLoadable.Populated].
     **/
    suspend fun load(content: SerializableList<T>) {
        send(content.toListLoadable())
    }

    /**
     * Sends a [ListLoadable.Failed].
     *
     * @param error [Throwable] to be set as the [ListLoadable.Failed.error].
     **/
    suspend fun fail(error: Throwable) {
        send(ListLoadable.Failed(error))
    }

    /**
     * Sends the given [listLoadable].
     *
     * @param listLoadable [ListLoadable] to be sent.
     **/
    protected abstract suspend fun send(listLoadable: ListLoadable<T>)
}
