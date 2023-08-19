package com.jeanbarrossilva.loadable.list.flow

import com.jeanbarrossilva.loadable.list.ListLoadable
import com.jeanbarrossilva.loadable.list.SerializableList
import com.jeanbarrossilva.loadable.list.serializableListOf
import com.jeanbarrossilva.loadable.list.toListLoadable
import java.io.NotSerializableException
import kotlinx.coroutines.flow.MutableStateFlow

/** Creates a [MutableStateFlow] with a [ListLoadable.Loading] as its initial value. **/
fun <T> listLoadableFlow(): MutableStateFlow<ListLoadable<T>> {
    return MutableStateFlow(ListLoadable.Loading())
}

/**
 * Returns a [MutableStateFlow] whose [value][MutableStateFlow.value] is a [ListLoadable] that
 * matches the given [content].
 *
 * @param content [Array] from which the [ListLoadable] will be created.
 * @throws NotSerializableException If any of the [content]'s elements cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> listLoadableFlowOf(vararg content: T): MutableStateFlow<ListLoadable<T>> {
    return listLoadableFlowOf(serializableListOf(*content))
}

/**
 * Returns a [MutableStateFlow] whose [value][MutableStateFlow.value] is a [ListLoadable] that
 * matches the given [content].
 *
 * @param content [SerializableList] from which the [ListLoadable] will be created.
 **/
fun <T> listLoadableFlowOf(content: SerializableList<T>): MutableStateFlow<ListLoadable<T>> {
    return MutableStateFlow(content.toListLoadable())
}
