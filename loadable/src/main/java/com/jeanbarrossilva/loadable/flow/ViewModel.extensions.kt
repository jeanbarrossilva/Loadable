package com.jeanbarrossilva.loadable.flow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeanbarrossilva.loadable.Loadable
import java.io.Serializable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch

/**
 * Creates a [MutableStateFlow] with the given [initialValue] that gets all of [origin]'s values
 * emitted to it through the [viewModelScope].
 *
 * @param initialValue [Loaded][Loadable.Loaded] value to be first held by the [MutableStateFlow].
 * @param origin [Flow] that's the source of the emitted values.
 **/
@Suppress("KDocUnresolvedReference")
fun <T : Serializable?> ViewModel.loadable(
    initialValue: T,
    origin: suspend () -> Flow<Loadable<T>>
): MutableStateFlow<Loadable<T>> {
    return loadable(initialValue).apply {
        viewModelScope.launch {
            emitAll(origin())
        }
    }
}
