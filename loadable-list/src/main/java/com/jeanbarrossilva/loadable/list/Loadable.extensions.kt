package com.jeanbarrossilva.loadable.list

import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable

/** Converts this [Loadable] into a [ListLoadable]. **/
fun <T : Serializable?> Loadable<SerializableList<T>>.toListLoadable(): ListLoadable<T> {
    return when (this) {
        is Loadable.Loading -> ListLoadable.Loading()
        is Loadable.Loaded -> content.toListLoadable()
        is Loadable.Failed -> ListLoadable.Failed(error)
    }
}
