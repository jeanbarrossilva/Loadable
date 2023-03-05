package com.jeanbarrossilva.loadable.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import com.jeanbarrossilva.loadable.Loadable
import com.jeanbarrossilva.loadable.LoadableScope
import com.jeanbarrossilva.loadable.scope.FlowCollectorLoadableScope
import java.io.Serializable
import kotlin.experimental.ExperimentalTypeInference
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

/** Collects the given [Flow] as a [State] with [Loadable.Loading] as its initial value. **/
@Composable
fun <T : Serializable?> Flow<Loadable<T>>.collectAsState(): State<Loadable<T>> {
    return collectAsState(initial = Loadable.Loading())
}

/** Returns a [Flow] containing only [failed][Loadable.Failed] values. **/
fun <T : Serializable?> Flow<Loadable<T>>.filterIsFailed(): Flow<Loadable.Failed<T>> {
    return filterIsInstance()
}

/** Returns a [Flow] containing only [loaded][Loadable.Loaded] values. **/
fun <T : Serializable?> Flow<Loadable<T>>.filterIsLoaded(): Flow<Loadable.Loaded<T>> {
    return filterIsInstance()
}

/**
 * Maps each emitted [Loadable] with the given [transform].
 *
 * @param transform Transformation to be done to the [Loadable]s.
 **/
fun <I : Serializable?, O : Serializable?> Flow<Loadable<I>>.innerMap(transform: suspend (I) -> O):
    Flow<Loadable<O>> {
    return map { loadable: Loadable<I> ->
        loadable.map {
            transform(it)
        }
    }
}

fun <T : Serializable?> Flow<T>.loadable(): Flow<Loadable<T>> {
    return flow {
        map { Loadable.Loaded(it) }
            .onStart { emit(Loadable.Loading()) }
            .catchAsFailed()
            .collect(::emit)
    }
}

/**
 * Unwraps [Loadable.Loaded] emissions and returns a [Flow] containing only their
 * [Loadable.Loaded.value]s.
 **/
fun <T : Serializable?> Flow<Loadable<T>>.unwrap(): Flow<T> {
    return filterIsLoaded().map {
        it.value
    }
}

/**
 * Creates a [Flow] of [Loadable]s and emits them through [loading], with a [LoadableScope].
 *
 * @param loading Operations to be made on the [LoadableScope] responsible for emitting [Loadable]s
 * sent to it to the created [Flow].
 **/
fun <T : Serializable?> loadable(loading: suspend LoadableScope<T>.() -> Unit):
    Flow<Loadable<T>> {
    return flow {
        FlowCollectorLoadableScope(this).apply {
            load()
            loading()
        }
    }
}

/**
 * Creates a [Flow] through [channelFlow] with a [Loadable.Loading] as its initial value.
 *
 * @param block Production to be run whenever a terminal operator is applied to the resulting
 * [Flow].
 **/
@OptIn(ExperimentalTypeInference::class)
fun <T : Serializable?> loadableChannelFlow(
    @BuilderInference block: suspend ProducerScope<Loadable<T>>.() -> Unit
): Flow<Loadable<T>> {
    return channelFlow {
        send(Loadable.Loading())
        block()
    }
        .catchAsFailed()
}

/** Catches thrown exceptions by emitting a [Loadable.Failed]. **/
private fun <T : Serializable?> Flow<Loadable<T>>.catchAsFailed(): Flow<Loadable<T>> {
    return catch {
        emit(Loadable.Failed(it))
    }
}
