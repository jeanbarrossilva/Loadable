package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable

/**
 * [Loadable]-like structure for representing different stages of an asynchronously-loaded
 * [SerializableList], as well as its "population" state.
 **/
sealed interface ListLoadable<T> {
    /** Whether this [ListLoadable] has been successfully loaded. **/
    val isLoaded: Boolean

    /** Stage in which the [SerializableList] is loading. **/
    class Loading<T> : ListLoadable<T> {
        override val isLoaded = false

        override fun toLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Loading()
        }
    }

    /** Stage in which the [SerializableList] has been loaded but it's empty. **/
    class Empty<T> : ListLoadable<T> {
        override val isLoaded = true

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
    value class Populated<T>(val content: SerializableList<T>) : ListLoadable<T> {
        override val isLoaded
            get() = true

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
    value class Failed<T>(val error: Throwable) : ListLoadable<T> {
        override val isLoaded
            get() = false

        override fun toLoadable(): Loadable<SerializableList<T>> {
            return Loadable.Failed(error)
        }
    }

    /** Converts this [ListLoadable] into a [Loadable]. **/
    fun toLoadable(): Loadable<SerializableList<T>>
}
