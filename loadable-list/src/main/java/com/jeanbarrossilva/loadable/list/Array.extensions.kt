package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Serializability
import java.io.NotSerializableException

/**
 * Converts this [Array] into a [SerializableList].
 *
 * @param serializability Determines whether each of the elements should be serializable.
 * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED] and
 * any of the elements cannot be serialized.
 * @throws NotSerializableException If any of the elements cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> Array<out T>.toSerializableList(serializability: Serializability = Serializability.default):
    SerializableList<T> {
    return serializableListOf(*this, serializability = serializability)
}
