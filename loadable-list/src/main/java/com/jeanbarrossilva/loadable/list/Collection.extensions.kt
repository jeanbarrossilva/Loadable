package com.jeanbarrossilva.loadable.list

import java.io.NotSerializableException

/**
 * Converts this [Collection] it into a [SerializableList].
 *
 * @throws NotSerializableException If any of the elements cannot be serialized.
 **/
@Throws(NotSerializableException::class)
inline fun <reified T> Collection<T>.toSerializableList(): SerializableList<T> {
    return toTypedArray().toSerializableList()
}
