package com.jeanbarrossilva.loadable

import java.io.NotSerializableException

/**
 * [Content][Loadable.Loaded.content] of the given [Loadable] if it's [loaded][Loadable.Loaded];
 * otherwise, `null`.
 **/
inline val <T> Loadable<T>.contentOrNull
    get() = ifLoaded { this }

/**
 * Returns the result of the given [operation] that's ran on the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content]; if the [Loadable] is not a [Loadable.Loaded], returns `null`.
 *
 * @param operation Callback to be run on the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content].
 **/
inline fun <I, O> Loadable<I>.ifLoaded(operation: I.() -> O): O? {
    return if (this is Loadable.Loaded) content.operation() else null
}

/**
 * If the receiver [Loadable] is [loaded][Loadable.Loaded], applies [transform] on its
 * [content][Loadable.Loaded.content] and creates a new [Loadable.Loaded] containing it.
 *
 * Otherwise, creates an instance of a [Loadable] that's equivalent to the given one but with [O] as
 * its type parameter.
 *
 * @param transform Transformation to be done to the [loaded][Loadable.Loaded]
 * [content][Loadable.Loaded.content].
 * @throws NotSerializableException If this is [loaded][Loadable.Loaded] and the result of
 * [transform] cannot be serialized.
 **/
@Throws(NotSerializableException::class)
inline fun <I, O> Loadable<I>.map(transform: (I) -> O): Loadable<O> {
    return when (this) {
        is Loadable.Loading -> Loadable.Loading()
        is Loadable.Loaded -> Loadable.Loaded(transform(content))
        is Loadable.Failed -> Loadable.Failed(error)
    }
}
