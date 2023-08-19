package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.requireSerializable
import java.io.NotSerializableException
import java.io.Serializable

/**
 * [List] that conforms to [Serializable].
 *
 * @param elements Instances of [T] contained in this [SerializableList].
 * @throws NotSerializableException If any of the [elements] cannot be serialized.
 **/
@JvmInline
value class SerializableList<T>
@Throws(NotSerializableException::class)
internal constructor(private val elements: List<T>) : List<T> by elements, Serializable {
    init {
        forEach(::requireSerializable)
    }
}
