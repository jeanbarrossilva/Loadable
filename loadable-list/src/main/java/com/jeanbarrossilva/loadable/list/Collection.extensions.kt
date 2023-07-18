package com.jeanbarrossilva.loadable.list

/** Converts this [Collection] it into a [SerializableList]. **/
inline fun <reified T> Collection<T>.toSerializableList(): SerializableList<T> {
    return toTypedArray().toSerializableList()
}
