package com.jeanbarrossilva.loadable.utils

import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable

/**
 * Converts the given [Serializable] into a [Loadable]. If it isn't `null`, returns a
 * [Loadable.Loaded] that wraps the object; otherwise, returns a [Loadable.Loading].
 **/
fun <T : Serializable> T?.toLoadable(): Loadable<T> {
    return this?.let { Loadable.Loaded(it) } ?: Loadable.Loading()
}
