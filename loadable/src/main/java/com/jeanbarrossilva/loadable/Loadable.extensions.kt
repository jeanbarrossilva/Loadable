package com.jeanbarrossilva.loadable.utils

import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable

/**
 * [Value][Loadable.Loaded.value] of the given [Loadable] if it's [loaded][Loadable.Loaded];
 * otherwise, `null`.
 **/
inline val <T : Serializable?> Loadable<T>.valueOrNull
    get() = ifLoaded { this }

/**
 * Returns the result of the given [operation] that's ran on the [loaded][Loadable.Loaded]
 * [value][Loadable.Loaded.value]; if the [Loadable] is not a [Loadable.Loaded], returns `null`.
 *
 * @param operation Lambda whose result will get returned if this is a [Loadable.Loaded].
 **/
inline fun <I : Serializable?, O> Loadable<I>.ifLoaded(operation: I.() -> O): O? {
    return if (this is Loadable.Loaded) value.operation() else null
}

/**
 * If the receiver [Loadable] is [loaded][Loadable.Loaded], applies [transform] on its
 * [value][Loadable.Loaded.value] and creates a new [Loadable.Loaded] containing it.
 *
 * Otherwise, creates an instance of a [Loadable] that's equivalent to the given one but with [O] as
 * its type parameter.
 *
 * @param transform Transformation to be done to the [loaded][Loadable.Loaded]
 * [value][Loadable.Loaded.value].
 **/
inline fun <I : Serializable?, O : Serializable?> Loadable<I>.map(transform: (I) -> O):
    Loadable<O> {
    return when (this) {
        is Loadable.Loading -> Loadable.Loading()
        is Loadable.Loaded -> Loadable.Loaded(transform(value))
        is Loadable.Failed -> Loadable.Failed(error)
    }
}
