package com.jeanbarrossilva.loadable.list.flow

import com.jeanbarrossilva.loadable.Serializability
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
 * @param serializability Determines whether each [content] element should be serializable.
 * @throws NotSerializableException If [serializability] is [enforced][Serializability.ENFORCED] and
 * any of the [content]'s elements cannot be serialized.
 **/
@Throws(NotSerializableException::class)
fun <T> listLoadableFlowOf(
    vararg content: T,
    serializability: Serializability = Serializability.default
): MutableStateFlow<ListLoadable<T>> {
    return listLoadableFlowOf(serializableListOf(*content, serializability = serializability))
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
