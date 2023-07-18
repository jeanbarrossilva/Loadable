package com.jeanbarrossilva.loadable.list

/** Converts this [Array] into a [SerializableList]. **/
fun <T> Array<out T>.serialize(): SerializableList<T> {
    return serializableListOf(*this)
}
