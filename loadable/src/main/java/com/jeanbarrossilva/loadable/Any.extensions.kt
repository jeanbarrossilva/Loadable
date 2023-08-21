package com.jeanbarrossilva.loadable

import java.io.NotSerializableException

/**
 * Converts this into a [Loadable].
 *
 * @return If the receiver is...
 *
 * - [Throwable], [Loadable.Failed];
 * - non-`null` [T] (with [T] being a non-`null` type), [Loadable.Loaded];
 * - `null` (with [T] being a nullable type), [Loadable.Loaded];
 * - `null` (with [T] being a non-`null` type), [Loadable.Loading];
 * - of a type other than [T]`?`, `null`.
 * @throws NotSerializableException If this is a [T] and cannot be serialized.
 **/
@Throws(NotSerializableException::class)
inline fun <reified T> Any?.loadable(): Loadable<T>? {
    return when (this) {
        is Throwable -> Loadable.Failed(this)
        is T -> Loadable.Loaded(this)
        null -> Loadable.Loading()
        else -> null
    }
}
