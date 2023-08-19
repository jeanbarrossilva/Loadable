package com.jeanbarrossilva.loadable.list

import java.io.NotSerializableException

/**
 * Converts this [Array] into a [SerializableList].
 *
 * @throws NotSerializableException If any of the elements cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> Array<out T>.toSerializableList(): SerializableList<T> {
    return serializableListOf(*this)
}
