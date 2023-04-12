package com.jeanbarrossilva.loadable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/** Different stages of asynchronously-loaded content. **/
sealed interface Loadable<T : Serializable?> : Parcelable {
    /** Stage in which the content is being loaded and, therefore, is temporarily unavailable. **/
    @Parcelize
    class Loading<T : Serializable?> : Loadable<T> {
        override fun toString(): String {
            return "Loading"
        }
    }

    /**
     * Stage in which the content has been loaded and is represented by [value].
     *
     * @param value Content that's been successfully loaded.
     **/
    @Parcelize
    data class Loaded<T : Serializable?>(val value: T) : Loadable<T>

    /**
     * Stage in which the content has failed to load and threw [error].
     *
     * @param error [Throwable] that's been thrown while trying to load the content.
     **/
    @Parcelize
    data class Failed<T : Serializable?>(val error: Throwable) : Loadable<T>
}
