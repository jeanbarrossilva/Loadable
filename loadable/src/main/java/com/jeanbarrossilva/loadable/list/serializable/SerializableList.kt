package com.jeanbarrossilva.loadable.list.serializable

import java.io.Serializable

/**
 * [List] that conforms to [Serializable].
 *
 * @param elements Instances of [T] contained in this [SerializableList].
 **/
@JvmInline
value class SerializableList<T> internal constructor(private val elements: List<T>) :
    List<T> by elements, Serializable
