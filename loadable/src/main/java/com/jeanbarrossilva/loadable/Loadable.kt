package com.jeanbarrossilva.loadable

import java.io.NotSerializableException
import java.io.Serializable

/** Different stages of asynchronously-loaded content. **/
sealed interface Loadable<T> : Serializable {
    /** Stage in which the content is being loaded and, therefore, is temporarily unavailable. **/
    class Loading<T> : Loadable<T> {
        override fun toString(): String {
            return "Loading"
        }
    }

    /**
     * Stage in which the content has been successfully loaded.
     *
     * @param content Value that's been loaded.
     * @throws NotSerializableException If [content] cannot be serialized.
     **/
    @JvmInline
    value class Loaded<T>
    @Throws(NotSerializableException::class)
    constructor(val content: T) : Loadable<T> {
        init {
            requireSerializable(content)
        }
    }

    /**
     * Stage in which the content has failed to load and threw [error].
     *
     * @param error [Throwable] that's been thrown while trying to load the content.
     **/
    @JvmInline
    value class Failed<T>(val error: Throwable) : Loadable<T>
}
