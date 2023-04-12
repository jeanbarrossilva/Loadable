package com.jeanbarrossilva.loadable

import java.io.Serializable

/**
 * Converts this into a [Loadable].
 *
 * @return If the receiver is...
 *
 * - [Throwable], [Loadable.Failed];
 * - non-`null` [T] (with [T] being a non-`null` type), [Loadable.Loaded];
 * - `null` (with [T] being a nullable type), [Loadable.Loaded];
 * - `null` (with [T] being a non-`null` type), [Loadable.Loading].
 *
 * When it doesn't match any of the aforementioned criteria, `null` is returned instead.
 **/
inline fun <reified T : Serializable?> Any?.loadable(): Loadable<T>? {
    return when (this) {
        is Throwable -> Loadable.Failed(this)
        is T -> Loadable.Loaded(this)
        null -> Loadable.Loading()
        else -> null
    }
}
