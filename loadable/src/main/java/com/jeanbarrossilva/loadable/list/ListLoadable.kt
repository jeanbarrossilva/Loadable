package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.list.serializable.SerializableList
import com.jeanbarrossilva.loadable.list.serializable.emptySerializableList
import java.io.Serializable

/**
 * [Loadable]-like structure for representing different stages of an asynchronously-loaded
 * [SerializableList], as well as its "population" state.
 **/
sealed interface ListLoadable<T : Serializable?> {
    /** Stage in which the [SerializableList] is loading. **/
    class Loading<T : Serializable?> : ListLoadable<T> {
        override fun toLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Loading()
        }
    }

    /** Stage in which the [SerializableList] has been loaded but it's empty. **/
    class Empty<T : Serializable?> : ListLoadable<T> {
        override fun toLoadable(): Loadable<SerializableList<T>> {
            val content = emptySerializableList<T>()
            return Loadable.Loaded(content)
        }
    }

    /**
     * Stage in which the [SerializableList] has been loaded and is populated.
     *
     * @param content The non-empty [SerializableList] itself, containing its elements.
     * @throws IllegalArgumentException If [content] is empty.
     */
    @JvmInline
    value class Populated<T : Serializable?>(val content: SerializableList<T>) : ListLoadable<T> {
        init {
            require(content.isNotEmpty()) {
                "Cannot create a populated ListLoadable with an empty SerializableList."
            }
        }

        override fun toLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Loaded(content)
        }
    }

    /**
     * Stage in which the [SerializableList] has failed to load and [error] has been thrown.
     *
     * @param error [Throwable] that's been thrown while trying to load the [SerializableList].
     **/
    @JvmInline
    value class Failed<T : Serializable?>(val error: Throwable) : ListLoadable<T> {
        override fun toLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Failed(error)
        }
    }

    /** Converts this [ListLoadable] into a [Loadable]. **/
    fun toLoadable(): Loadable<SerializableList<T>>
}
