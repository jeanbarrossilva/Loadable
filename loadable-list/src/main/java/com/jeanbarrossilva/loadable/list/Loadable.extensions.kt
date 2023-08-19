package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable

/** Converts this [Loadable] into a [ListLoadable]. **/
fun <T> Loadable<SerializableList<T>>.toListLoadable(): ListLoadable<T> {
    return when (this) {
        is Loadable.Loading -> ListLoadable.Loading()
        is Loadable.Loaded -> content.toListLoadable()
        is Loadable.Failed -> ListLoadable.Failed(error)
    }
}
