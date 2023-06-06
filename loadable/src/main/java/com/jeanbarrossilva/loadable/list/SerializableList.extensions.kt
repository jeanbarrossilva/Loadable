package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.list.serializable.SerializableList
import java.io.Serializable

/** Converts this [SerializableList] into a [ListLoadable]. **/
fun <T : Serializable?> SerializableList<T>.asListLoadable(): ListLoadable<T> {
    return if (isEmpty()) ListLoadable.Empty() else ListLoadable.Populated(this)
}
