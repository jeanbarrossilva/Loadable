package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.list.serializable.SerializableList
import com.jeanbarrossilva.loadable.list.serializable.serialize
import com.jeanbarrossilva.loadable.map
import java.io.Serializable

/**
 * Returns the first element of the [SerializableList] to match the given [predicate], wrapped by a
 * [Loadable] matching the state of this [ListLoadable].
 *
 * @param predicate Condition to which the element to be found should conform.
 **/
fun <T : Serializable?> ListLoadable<T>.find(predicate: (T) -> Boolean): Loadable<T?> {
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
fun <I : Serializable?, O> ListLoadable<I>.ifPopulated(transform: SerializableList<I>.() -> O): O? {
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
inline fun <I : Serializable?, reified O : Serializable?> ListLoadable<I>.mapNotNull(
    transform: (I) -> O?
): ListLoadable<O> {
    return when (this) {
        is ListLoadable.Loading -> ListLoadable.Loading()
        is ListLoadable.Empty -> ListLoadable.Empty()
        is ListLoadable.Populated -> content.mapNotNull(transform).serialize<O>().asListLoadable()
        is ListLoadable.Failed -> ListLoadable.Failed(error)
    }
}

/** Converts this [Loadable] into a [ListLoadable]. **/
fun <T : Serializable?> Loadable<SerializableList<T>>.asListLoadable(): ListLoadable<T> {
    return when (this) {
        is Loadable.Loading -> ListLoadable.Loading()
        is Loadable.Loaded -> content.asListLoadable()
        is Loadable.Failed -> ListLoadable.Failed(error)
    }
}
