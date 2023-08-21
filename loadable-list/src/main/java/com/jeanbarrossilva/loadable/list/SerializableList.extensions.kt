package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Serializability
import java.io.NotSerializableException

/** Converts this [SerializableList] into a [ListLoadable]. **/
fun <T> SerializableList<T>.toListLoadable(): ListLoadable<T> {
    return if (isEmpty()) ListLoadable.Empty() else ListLoadable.Populated(this)
}

/**
 * Creates a [SerializableList] without elements.
 *
 * @see serializableListOf
 **/
fun <T> emptySerializableList(): SerializableList<T> {
    return serializableListOf()
}

/**
 * Creates a new [SerializableList] with the given [elements].
 *
 * @param elements Elements to be added to the [SerializableList].
 * @param serializability Determines whether each of the [elements] should be serializable.
 * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED] and
 * any of the [elements] cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> serializableListOf(
    vararg elements: T,
    serializability: Serializability = Serializability.default
): SerializableList<T> {
    val elementsAsList = elements.toList()
    return SerializableList(serializability, elementsAsList)
}
