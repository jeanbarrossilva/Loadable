package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.map

/**
 * Returns the first element of the [SerializableList] to match the given [predicate], wrapped by a
 * [Loadable] matching the state of this [ListLoadable].
 *
 * @param predicate Condition to which the element to be found should conform.
 **/
fun <T> ListLoadable<T>.find(predicate: (T) -> Boolean): Loadable<T?> {
    return toLoadable().map { content ->
        content.find(predicate)
    }
}

/**
 * Returns the result of the given [transform] if this [ListLoadable] is
 * [populated][ListLoadable.Populated]; otherwise, `null`.
 *
 * @param transform Transformation to be made to the [SerializableList].
 **/
fun <I, O> ListLoadable<I>.ifPopulated(transform: SerializableList<I>.() -> O): O? {
    return if (this is ListLoadable.Populated) content.transform() else null
}

/**
 * Applies [transform] to the [SerializableList]'s elements and returns the ones that aren't
 * `null` if this is [populated][ListLoadable.Populated]; otherwise, creates a new instance with
 * [O] as the type parameter.
 *
 * @param transform Transformation to be made to the elements of the
 * [populated][ListLoadable.Populated] [SerializableList].
 **/
inline fun <I, reified O> ListLoadable<I>.mapNotNull(transform: (I) -> O?): ListLoadable<O> {
    return when (this) {
        is ListLoadable.Loading ->
            ListLoadable.Loading()
        is ListLoadable.Empty ->
            ListLoadable.Empty()
        is ListLoadable.Populated ->
            content.mapNotNull(transform).toSerializableList<O>().toListLoadable()
        is ListLoadable.Failed ->
            ListLoadable.Failed(error)
    }
}
