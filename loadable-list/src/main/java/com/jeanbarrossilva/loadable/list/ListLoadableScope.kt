package com.jeanbarrossilva.loadable.list

import java.io.Serializable

/** Scope through which [ListLoadable]s are sent. **/
abstract class ListLoadableScope<T : Serializable?> internal constructor() {
    /** Sends a [ListLoadable.Loading]. **/
    suspend fun load() {
        send(ListLoadable.Loading())
    }

    /**
     * Sends a [ListLoadable] that matches the given [content].
     *
     * @param content [Array] to be converted into a [SerializableList] and sent either as a
     * [ListLoadable.Empty] or a [ListLoadable.Populated].
     * @see Array.serialize
     **/
    suspend fun load(vararg content: T) {
        load(content.serialize())
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
