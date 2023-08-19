package com.jeanbarrossilva.loadable

import java.io.ByteArrayOutputStream
import java.io.NotSerializableException
import java.io.ObjectOutputStream

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

/**
 * Requires the [value] to be serializable.
 *
 * @param value Object whose serialization capability will be required.
 * @return The [value] itself.
 * @throws NotSerializableException If the [value] cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> requireSerializable(value: T): T {
    ByteArrayOutputStream().use { byteArrayOutputStream ->
        ObjectOutputStream(byteArrayOutputStream).use { objectOutputStream ->
            objectOutputStream.writeObject(value)
        }
    }
    return value
}
