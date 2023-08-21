package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Serializability
import java.io.NotSerializableException
import java.io.Serializable

/**
 * [List] that conforms to [Serializable].
 *
 * @param serializability Determines whether each of the [elements] should be serializable.
 * @param elements Instances of [T] contained in this [SerializableList].
 * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED] and
 * any of the [elements] cannot be serialized.
 **/
data class SerializableList<T>
@Throws(NotSerializableException::class)
internal constructor(val serializability: Serializability, private val elements: List<T>) :
    List<T> by elements, Serializable {
    init {
        forEach(serializability::check)
    }
}
