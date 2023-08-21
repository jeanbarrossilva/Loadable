package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Serializability
import java.io.NotSerializableException

/**
 * Converts this [Collection] it into a [SerializableList].
 *
 * @param serializability Determines whether each of the elements should be serializable.
 * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED] and
 * any of the elements cannot be serialized.
 **/
@Throws(NotSerializableException::class)
inline fun <reified T> Collection<T>.toSerializableList(
    serializability: Serializability = Serializability.default
): SerializableList<T> {
    return toTypedArray().toSerializableList(serializability)
}
